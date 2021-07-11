package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

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

        //recibir calificaciones

        try {
            val alumnito = alumno as Alumno
            val usr = alumnito.id.toString()

            val url = "${resources.getString(R.string.wsm)}/verCalif.php?usr=$usr"

            println("id: $usr")

            val params = HashMap<String,String>()
            params.put("usr",usr)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")
                    println(output)
                                                                       //Mostrar estos datos en aplicacion

                }

            }.consumeGet(this,url)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }




    }
}