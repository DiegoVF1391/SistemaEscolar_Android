package mx.edu.itm.mark.tap_u4_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val alumno = intent.getSerializableExtra("alumno") as Alumno
        println("Alumno recibido: $alumno")
    }
}