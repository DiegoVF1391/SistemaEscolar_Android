package mx.edu.itm.mark.tap_u4_proyecto.models

import java.io.Serializable

data class Alumno(
    val id: Int,
    val n_control: String,
    val contrasenia: String,
    val nombre: String,
    val semestre: Int
):Serializable
