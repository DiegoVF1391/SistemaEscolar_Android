package mx.edu.itm.mark.tap_u4_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno

class CalificacionesActivity : AppCompatActivity() {

    private lateinit var textCalificaciones: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calificaciones)

        textCalificaciones = findViewById(R.id.textCalificaciones)

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        val alumnito = alumno as Alumno
        val nombre = alumnito.nombre
        val semestre = alumnito.semestre
        val nocontrol = alumnito.n_control

        textCalificaciones.text = "Calificaciones de: $nombre"


    }
}