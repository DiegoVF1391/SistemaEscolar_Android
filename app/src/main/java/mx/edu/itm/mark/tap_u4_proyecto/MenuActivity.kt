package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

class MenuActivity : AppCompatActivity() {

    private lateinit var btnDatos: Button
    private lateinit var btnExamen: Button
    private lateinit var btnCalificaciones: Button
    private lateinit var textSaludo: TextView
    //private lateinit var spinnerMaterias: Spinner
    private lateinit var materia: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        //

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        btnDatos = findViewById(R.id.btnDatos)
        btnExamen = findViewById(R.id.btnExamen)
        btnCalificaciones = findViewById(R.id.btnCalificaciones)
       // val materiaT: MutableList<String> = ArrayList()
        val materiaT = ArrayList<String>()

        textSaludo = findViewById(R.id.textSaludo)

          //Saludar alumno ... OBTENER EL ATRIBUTO NOMBRE NADAMAS
        val nombresito  = alumno as Alumno
        val nombresote = alumno.nombre
        val id= alumno.id.toString()

        textSaludo.text= "Bienvenido: $nombresote"


        btnDatos.setOnClickListener{
            val intent = Intent(this, DatosActivity::class.java)
            //mandando el objeto con los datos del alumno al activity datos
            intent.putExtra("alumno",alumno)

            startActivity(intent)
        }

        btnExamen.setOnClickListener{
            val intent = Intent(this, ExamenActivity::class.java)
            //mandando el objeto con los datos del alumno al activity datos
            intent.putExtra("alumno",alumno)

            startActivity(intent)
        }

        btnCalificaciones.setOnClickListener {
            val intent = Intent(this, CalificacionesActivity::class.java)
            //mandando el objeto con los datos del alumno al activity datos
            intent.putExtra("alumno",alumno)

            startActivity(intent)

        }


     //recibir Materias
        try {
            val url = "${resources.getString(R.string.wsm)}/verCalif.php"
            val params = HashMap<String,String>()
            params.put("usr",id)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")

                    for(i in 0..output.length()-1){
                        var jmateria = JSONObject(output[i].toString())
                        println("Materia: ${jmateria.getString("nombre_materia")}")
                        //LLENAR SPINNER de materias
                        val a = jmateria.getString("nombre_materia").toString()
                        materiaT.add(a)

                    }
                    println("Materias: ${materiaT}")

                    //Mostrar estos datos en aplicacion
                    //spinnerMaterias.
                    val spinnerMaterias = findViewById<Spinner>(R.id.spinnerMaterias)
                    spinnerMaterias.adapter = ArrayAdapter(this@MenuActivity, android.R.layout.simple_list_item_1, materiaT)


                }
            }.consumePost(this,url,params)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }



    }


}