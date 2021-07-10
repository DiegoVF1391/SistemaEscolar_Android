package mx.edu.itm.mark.tap_u4_proyecto.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Alumno(
    @SerializedName("id")
    val id: Int,
    @SerializedName("n_control")
    val n_control: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("semestre")
    val semestre: Int

):Serializable