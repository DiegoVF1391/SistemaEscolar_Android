package mx.edu.itm.mark.tap_u4_proyecto

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONArray
import org.json.JSONObject

class ExamenActivity : AppCompatActivity() {

    private lateinit var textExamen: TextView
    private lateinit var textPregunta: TextView
    private lateinit var spinnerRespuestas: Spinner
    private lateinit var btnSiguiente: Button
    private lateinit var textp: TextView
    private var cont = 0
    private  var numRespuestas:Int = 0
    private  var idP:Int=0
    private  var cont2:Int = 0
    private var respuestaCorrecta =""

    private val respuestasT = ArrayList<String>()

    private lateinit var output: JSONArray
    private lateinit var output2: JSONArray

    //val urlp = "${resources.getString(R.string.wsm)}/preguntas.php"
    //val urlr = "${resources.getString(R.string.wsm)}/pyr.php"
    //val urlc = "${resources.getString(R.string.wsm)}/calificar.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examen)

        textExamen = findViewById(R.id.textExamen)
        textPregunta = findViewById(R.id.textPregunta)
        spinnerRespuestas = findViewById(R.id.spinnerRespuestas)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        textp = findViewById(R.id.textp)

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        val alumnito = alumno as Alumno
        val semestre = alumnito.semestre.toString()

        val materia1 = intent.getStringExtra("materia1")

        textExamen.text = "Examen de: $materia1"


        //Recibir preguntas
        //recibir calificaciones

        try {
            val url = "${resources.getString(R.string.wsm)}/preguntas.php"

            println("semestre: $semestre")
            println("materia: $materia1")

            val params = HashMap<String,String>()
            params.put("materia",materia1.toString())
            params.put("semestre",semestre)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    output = json.getJSONArray("output")
                    println(output)
                    //Mostrar estos datos en aplicacion

                    val jPregunta = JSONObject(output[0].toString())
                    textp.text = "Pregunta ${cont+1} :"
                    textPregunta.text = jPregunta.getString("pregunta")
                    idP = jPregunta.getString("id").toInt()
                }

            }.consumePost(this,url,params)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }

        //respuestas
        try {
            val url = "${resources.getString(R.string.wsm)}/pyr.php"

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    output = json.getJSONArray("output")
                    println(output)
                    //Mostrar estos datos en aplicacion
                    //val jPregunta = JSONObject(output[0].toString())
                    for(i in 0..output.length()-1){
                        val jRespuesta= JSONObject(output[i].toString())
                        println(jRespuesta.getString("respuesta"))
                        println(jRespuesta.getString("id_pregunta"))


                        //verificar id de la pregunta
                        println("idP: $idP y getId: ${jRespuesta.getString("id_pregunta").toInt()}")
                        if(idP == jRespuesta.getString("id_pregunta").toInt()){
                            println("FUNCIONA ID")
                            respuestasT.add(jRespuesta.getString("respuesta"))

                        }

                    }
                    println("Arregloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
                    println(respuestasT)
                    spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)
                }

            }.consumeGet(this,url)

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }





        btnSiguiente.setOnClickListener {
            cont++
            //revisar()
            pregunta()
            println("JALAAAAAAAAAA")

            /*if(spinnerRespuestas.getSelectedItem().toString() == respuestaCorrecta){
                numRespuestas+=10
            }*/
        }

    }





    private fun pregunta(){
        if(cont<10){
            textPregunta.text = null
            respuestasT.clear()
            spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)

            val jPregunta = JSONObject(output[cont].toString())
            textp.text = "Pregunta ${cont+1} :"
            textPregunta.text = jPregunta.getString("pregunta")
            idP = jPregunta.getString("id").toInt()

            //respuestas
            try {
                val url = "${resources.getString(R.string.wsm)}/pyr.php"

                object: MyUtils(){
                    override fun formatResponse(response: String) {
                        val json = JSONObject(response)
                        output = json.getJSONArray("output")
                        println(output)
                        //Mostrar estos datos en aplicacion
                        //val jPregunta = JSONObject(output[0].toString())
                        for(i in 0..output.length()-1){
                            val jRespuesta= JSONObject(output[i].toString())
                            println(jRespuesta.getString("respuesta"))
                            println(jRespuesta.getString("id_pregunta"))


                            //verificar id de la pregunta
                            if(idP == jRespuesta.getString("id_pregunta").toInt()){
                                respuestasT.add(jRespuesta.getString("respuesta"))
                            }
                        }
                        println("Arregloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo")
                        println(respuestasT)
                        spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)
                    }

                }.consumeGet(this,url)

            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
            }

        }else{
            finish()

        }

    }

    private fun revisar(){

        for(i in 0..output2.length()-1){
            val re= JSONObject(output2[i].toString())
            val valor =  re.getString("respuesta")

            if(valor == spinnerRespuestas.getSelectedItem().toString()){

                if( re.getString("correcta").toInt() == 1){
                    numRespuestas+=10
                    respuestaCorrecta = re.getString("respuesta")
                }else{
                    numRespuestas+=0
                }
            }
        }

    }

}