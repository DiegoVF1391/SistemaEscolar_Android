package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno

class MenuActivity : AppCompatActivity() {

    private lateinit var btnDatos: Button
    private lateinit var btnExamen: Button
    private lateinit var textSaludo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        //

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        btnDatos = findViewById(R.id.btnDatos)
        btnExamen = findViewById(R.id.btnExamen)
        textSaludo = findViewById(R.id.textSaludo)

          //Saludar alumno ... OBTENER EL ATRIBUTO NOMBRE NADAMAS
        val nombresito = alumno

        textSaludo.text= "Bienvenido: $nombresito"


        btnDatos.setOnClickListener{
            val intent = Intent(this, DatosActivity::class.java)
            startActivity(intent)
            //mandando el objeto con los datos del alumno al activity datos
            intent.putExtra("alumno",alumno)
        }

        btnExamen.setOnClickListener{
            val intent = Intent(this, ExamenActivity::class.java)
            startActivity(intent)
        }
    }


}