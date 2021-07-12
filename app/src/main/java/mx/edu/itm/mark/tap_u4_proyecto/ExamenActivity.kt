package mx.edu.itm.mark.tap_u4_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

class ExamenActivity : AppCompatActivity() {

    private lateinit var textExamen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examen)

        textExamen = findViewById(R.id.textExamen)

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")
        val materia1 = intent.getStringExtra("materia1")

        textExamen.text = "Examen de: $materia1"

        val alumnito = alumno as Alumno
        val semestre = alumnito.semestre.toString()

        //Recibir preguntas
        //recibir calificaciones

        try {
            val url = "${resources.getString(R.string.wsm)}/preguntas.php"

            println("semestre: $semestre")

            val params = HashMap<String,String>()
            params.put("materia","Programacion I")
            params.put("semestre",semestre)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")
                    println(output)
                    //Mostrar estos datos en aplicacion

                }

            }.consumePost(this,url,params)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }




    }
}