package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno

class MenuActivity : AppCompatActivity() {

    private lateinit var btnDatos: Button
    private lateinit var btnExamen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnDatos = findViewById(R.id.btnDatos)
        btnExamen = findViewById(R.id.btnExamen)

        btnDatos.setOnClickListener{
            val intent = Intent(this, DatosActivity::class.java)
            startActivity(intent)
        }

        btnExamen.setOnClickListener{
            val intent = Intent(this, ExamenActivity::class.java)
            startActivity(intent)
        }
    }


}