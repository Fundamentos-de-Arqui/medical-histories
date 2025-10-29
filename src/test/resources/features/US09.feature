Feature: Actualizar historia clínica
    Escenario: Actualización de historia clínica
    Dado que el terapeuta se encuentra en una historia clínica
    Y añade información nueva
    Cuando selecciona guardar cambios
    Entonces debe generarse una copia del estado actual en auditoría
    Y el registro actual de la historia debe actualizarse
    Y mostrar un mensaje de confirmación
