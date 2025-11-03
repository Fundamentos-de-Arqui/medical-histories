package com.soulware.medicalhistory.infrastructure.adapters.in.messaging;

import com.soulware.medicalhistory.application.ports.in.GetAllClinicalFoldersUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.in.messaging.dtos.MedicalRecordFromProfilesListenerRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.messaging.dtos.ProfilesListenerRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.GetAllClinicalFolderAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.MedicalRecordDetailResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.PagedClinicalFoldersResponse;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebListener
public class MedicalRecordToProfilesListener implements ServletContextListener, MessageListener {

    private static final Logger logger = Logger.getLogger(MedicalRecordToProfilesListener.class.getName());

    private static Connection connection;
    private static Session session;
    private static MessageConsumer consumer;
    private static MessageProducer producer;
    private static boolean isInitialized = false;

    @Inject
    GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        synchronized (MedicalRecordToProfilesListener.class) {
            if (isInitialized) {
                logger.info("Listener already initialized, skipping...");
                return;
            }
            try {
                closeConnections();

                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
                connection = factory.createConnection("username", "password");
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Queue inputQueue = session.createQueue("medicalRecord_getProfiles");
                Queue outputQueue = session.createQueue("profiles_getMedicalRecord");

                consumer = session.createConsumer(inputQueue);
                producer = session.createProducer(outputQueue);

                consumer.setMessageListener(this);
                connection.start();

                isInitialized = true;
                logger.info("MedicalRecordToProfilesListener initialized successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to initialize listener: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Stopping MedicalRecordToProfilesListener...");
        closeConnections();
    }

    private static void closeConnections() {
        try {
            if (consumer != null) { consumer.close(); consumer = null; }
            if (producer != null) { producer.close(); producer = null; }
            if (session != null) { session.close(); session = null; }
            if (connection != null) { connection.close(); connection = null; }
            isInitialized = false;
            logger.info("Connections closed successfully.");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error closing connections: " + e.getMessage(), e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            String messageText = null;

            if (message instanceof TextMessage textMessage) {
                messageText = textMessage.getText();
                logger.info("Received TEXT message: " + messageText);
            } else if (message instanceof BytesMessage bytesMessage) {
                logger.info("Received BYTES message");
                long length = bytesMessage.getBodyLength();
                if (length > 0) {
                    byte[] data = new byte[(int) length];
                    bytesMessage.readBytes(data);
                    messageText = new String(data, "UTF-8");
                    logger.info("Converted BYTES to TEXT: " + messageText);
                } else {
                    logger.warning("Empty BytesMessage received");
                    return;
                }
            } else {
                logger.warning("Received unsupported message type: " + message.getClass().getSimpleName());
                return;
            }

            // Ahora messageText siempre contiene el contenido en texto
            Jsonb jsonb = JsonbBuilder.create();
            MedicalRecordFromProfilesListenerRequest request =
                    jsonb.fromJson(messageText, MedicalRecordFromProfilesListenerRequest.class);

            var record = getMedicalRecordByPatientAndVersionNumberUseCase
                    .getMedicalRecordByPatientAndVersionNumber(
                            new GetMedicalRecordByPatientAndVersionNumberQuery(
                                    request.patientId(), request.versionNumber()));

            String responseJson = jsonb.toJson(MedicalRecordDetailResponse.from(record, request.patientId()));

            TextMessage responseMessage = session.createTextMessage(responseJson);
            producer.send(responseMessage);

            logger.info("Sent medical record: " + responseJson);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message", e);
        }
    }

}