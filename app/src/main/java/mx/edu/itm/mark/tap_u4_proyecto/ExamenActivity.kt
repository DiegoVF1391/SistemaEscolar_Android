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
    private  var idP:Int = 0
    private  var cont2:Int = 0
    private var respuestaCorrecta =""
    private var elegida:String =""

    private lateinit var jRespuesta: JSONObject

    private val respuestasT = ArrayList<String>()

    private lateinit var output: JSONArray
    private lateinit var output2: JSONArray

    private var materia1:String =""
    private var semestre:String =""

    //val urlp = "${resources.getString(R.string.wsm)}/preguntas.php"
    //val urlr = "${resources.getString(R.string.wsm)}/pyr.php"
    //val urlc = "${resources.getString(R.string.wsm)}/calificar.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examen)

        var idP2:Int = 0


        textExamen = findViewById(R.id.textExamen)
        textPregunta = findViewById(R.id.textPregunta)
        spinnerRespuestas = findViewById(R.id.spinnerRespuestas)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        textp = findViewById(R.id.textp)

        //Obtener datos del alumno desde activity anterior..
        val alumno = intent.getSerializableExtra("alumno")

        val alumnito = alumno as Alumno
        semestre = alumnito.semestre.toString()

        materia1 = intent.getStringExtra("materia1").toString()

        textExamen.text = "Examen de: $materia1"


        //Recibir                               PREGUNTAS (PRIMERA)

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
                    println("                       EL OUTPUT...           ")
                    println(output)
                    //Mostrar estos datos en aplicacion

                    val jPregunta = JSONObject(output[0].toString())
                    textp.text = "Pregunta ${cont+1} :"
                    textPregunta.text = jPregunta.getString("pregunta")
                    println("                            LA PREGUNTA:  ID:  ${jPregunta.getString("id")}")
                    idP2 = jPregunta.getString("id").toInt()

                           //respuestas   (PRIMERA)
                    println("                                                     IDP2: ${idP2.toString()}")
                    try {
                        val url = "${resources.getString(R.string.wsm)}/verRespuestas.php?"

                        val params = HashMap<String,String>()
                        params.put("id",idP2.toString())

                        object: MyUtils(){
                            override fun formatResponse(response: String) {
                                val json = JSONObject(response)
                                output = json.getJSONArray("output")
                                //println(output)
                                //Mostrar estos datos en aplicacion
                                //val jPregunta = JSONObject(output[0].toString())
                                for(i in 0..output.length()-1){
                                    jRespuesta= JSONObject(output[i].toString())
                                    println(jRespuesta.getString("respuesta"))
                                    //println(jRespuesta.getString("id_pregunta"))

                                    //verificar id de la pregunta
                                    //println("idP: $idP y getId: ${jRespuesta.getString("id_pregunta").toInt()}")
                                    if(idP2 == jRespuesta.getString("id_pregunta").toInt()){
                                        //println("FUNCIONA ID")
                                        //println(jRespuesta.getString("correcta"))
                                        respuestasT.add(jRespuesta.getString("respuesta"))

                                        if(jRespuesta.getString("correcta").toInt() == 1){
                                            respuestaCorrecta = jRespuesta.getString("respuesta")
                                        }

                                    }

                                }
                                //println("                       ESTO NOS DA:   $response")
                                 println(respuestasT)
                                 spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)
                                elegida = spinnerRespuestas.getSelectedItem().toString()
                                println("                                    a VER : $elegida")
                            }

                        }.consumePost(this@ExamenActivity,url,params)

                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(this@ExamenActivity,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
                    }
                }

            }.consumePost(this,url,params)







        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }







        btnSiguiente.setOnClickListener {
            cont++
            //revisar()
            pregunta()
            println("JALAAAAAAAAAA")
            println("  correct:                              $respuestaCorrecta       ")
            println("  elegida                                    $elegida")


            if(elegida == respuestaCorrecta.toString()){
                numRespuestas+=10
            }
            println("Correctas                        $numRespuestas")
        }

    }





    private fun pregunta(){

        var idP3:Int = 0

        if(cont<10){
            textPregunta.text = null
            respuestasT.clear()
            spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)

            //Recibir                               PREGUNTAS (OTRAS)

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
                        println("                       EL OUTPUT...           ")
                        println(output)
                        //Mostrar estos datos en aplicacion

                        val jPregunta = JSONObject(output[cont].toString())
                        textp.text = "Pregunta ${cont+1} :"
                        textPregunta.text = jPregunta.getString("pregunta")
                        println("                            LA PREGUNTA:  ID:  ${jPregunta.getString("id")}")
                        idP3 = jPregunta.getString("id").toInt()

                               //respuestas   (OTRA)
                        println("                                                     IDP3: ${idP3.toString()}")
                        try {
                            val url = "${resources.getString(R.string.wsm)}/verRespuestas.php?"

                            val params = HashMap<String,String>()
                            params.put("id",idP3.toString())

                            object: MyUtils(){
                                override fun formatResponse(response: String) {
                                    val json = JSONObject(response)
                                    output = json.getJSONArray("output")
                                    //println(output)
                                    //Mostrar estos datos en aplicacion
                                    //val jPregunta = JSONObject(output[0].toString())
                                    for(i in 0..output.length()-1){
                                        jRespuesta= JSONObject(output[i].toString())
                                        println(jRespuesta.getString("respuesta"))
                                        //println(jRespuesta.getString("id_pregunta"))

                                        //verificar id de la pregunta
                                        //println("idP: $idP y getId: ${jRespuesta.getString("id_pregunta").toInt()}")
                                        if(idP3 == jRespuesta.getString("id_pregunta").toInt()){
                                            //println("FUNCIONA ID")
                                            //println(jRespuesta.getString("correcta"))
                                            respuestasT.add(jRespuesta.getString("respuesta"))

                                            if(jRespuesta.getString("correcta").toInt() == 1){
                                                respuestaCorrecta = jRespuesta.getString("respuesta")
                                            }

                                        }

                                    }
                                    //println("                       ESTO NOS DA:   $response")
                                    println(respuestasT)
                                    spinnerRespuestas.adapter = ArrayAdapter(this@ExamenActivity, android.R.layout.simple_list_item_1, respuestasT)
                                    elegida = spinnerRespuestas.getSelectedItem().toString()
                                    println("                                    a VER : $elegida")
                                }

                            }.consumePost(this@ExamenActivity,url,params)

                        }catch (e: Exception){
                            e.printStackTrace()
                            Toast.makeText(this@ExamenActivity,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
                        }
                    }

                }.consumePost(this,url,params)


            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
            }


        }else{
            finish()
            println("                                             $numRespuestas")

        }

    }








    private fun revisar(){
        println(                        "JALA revisar")

        /*for(i in 0..output.length()-1){
            var re= JSONObject(output[i].toString())
            var valor =  re.getString("respuesta")*/

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
                        println("Correcta:                                                ${jRespuesta.getString("correcta")}")


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


           /* if(valor == spinnerRespuestas.getSelectedItem().toString()){

                if( re.getString("correcta").toInt() == 1){
                    numRespuestas+=10
                    respuestaCorrecta = re.getString("respuesta")
                }else{
                    numRespuestas+=0
                }
            }
        }*/

    }

}