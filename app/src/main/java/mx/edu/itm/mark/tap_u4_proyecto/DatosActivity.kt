package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

class DatosActivity : AppCompatActivity() {
    private lateinit var btnNueva: Button
    private lateinit var editNombre: TextView
    private lateinit var editNoControl: TextView
    private lateinit var editSemestre: TextView
    private lateinit var editPass: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        editNoControl = findViewById(R.id.editNoControl)
        editNombre = findViewById(R.id.editNombre)
        editSemestre = findViewById(R.id.editSemestre)
        editPass = findViewById(R.id.editPass)
        btnNueva = findViewById(R.id.btnNueva)

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        val alumnito = alumno as Alumno
        val nombre = alumnito.nombre
        val semestre = alumnito.semestre
        val nocontrol = alumnito.n_control


        //llenar campos
        editNoControl.text  = "No de control: $nocontrol"
        editNombre.text = "Nombre: $nombre"
        editSemestre.text = "Semestre ${semestre.toString()}"


        btnNueva.setOnClickListener{ cambiarPass() }
    }

    private fun cambiarPass(){
        if (editPass.text.isEmpty()){
            editPass.setError("La contraseña no debe estar vacia")
        }
        else
        {
            val url = "${resources.getString(R.string.wsm)}/newPass.php"


            val alumno = intent.getSerializableExtra("alumno")
            val alumnito = alumno as Alumno
            val id = alumnito.id.toString()
            val pass = editPass.text.toString()

            try {
                val params = HashMap<String,String>()
                params.put("id", id)
                params.put("pass",pass)

                object: MyUtils() {
                    override fun formatResponse(response: String) {
                       // val json = JSONObject(response)
                       // val output = json.getJSONArray("output")

                        println(response)
                    }
                }.consumePost(this,url,params)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
            }

        }


    }
}