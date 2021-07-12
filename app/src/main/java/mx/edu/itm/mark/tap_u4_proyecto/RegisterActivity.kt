package mx.edu.itm.mark.tap_u4_proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {

    private lateinit var editRegisterUsr: EditText
    private lateinit var editRegisterPass: EditText
    private lateinit var editRegisterName: EditText
    private lateinit var editRegisterSemester: EditText
    private lateinit var btnRegisterSave: Button
    private lateinit var btnRegisterCancel: Button

    private val MY_TIMEOUT = 5000
    private  var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editRegisterUsr = findViewById(R.id.editRegisterUsr)
        editRegisterPass = findViewById(R.id.editRegisterPass)
        editRegisterName = findViewById(R.id.editRegisterName)
        editRegisterSemester = findViewById(R.id.editRegisterSemester)
        btnRegisterSave = findViewById(R.id.btnRegisterSave)
        btnRegisterCancel = findViewById(R.id.btnRegisterCancel)

        btnRegisterSave.setOnClickListener{registrar()}
        btnRegisterCancel.setOnClickListener { finish() }
    }

    private fun registrar(){
        if(editRegisterUsr.text.isEmpty()){
            editRegisterUsr.setError("El usuario no debe ser vacio ")
            return
        }
        if(editRegisterPass.text.isEmpty()){
            editRegisterPass.setError("Contraseña no debe ser vacio ")
            return
        }
        if(editRegisterName.text.isEmpty()){
            editRegisterName.setError("El nombre no debe ser vacio ")
            return
        }
        if(editRegisterSemester.text.isEmpty()){
            editRegisterSemester.setError("El semestre no debe ser vacio ")
            return
        }

        val usr = editRegisterUsr.text.toString()
        val pass = editRegisterPass.text.toString()
        val nombre = editRegisterName.text.toString()
        val sem = editRegisterSemester.text.toString()


       try {  //ESTE FUNCIONA


            val url = "${resources.getString(R.string.wsm)}/registro.php"

            val params = HashMap<String,String>()
            params.put("ncontrol",usr)
            params.put("pass",pass)
            params.put("name",nombre)
            params.put("sem",sem)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")

                    if(output.getString(0).equals("Ok")){
                        Toast.makeText(this@RegisterActivity,
                            "Se registro correctamente",
                        Toast.LENGTH_LONG).show()
                        println("Registrado correctamente")
                    }
                }
            }.consumePost(this,url,params)

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }

        //VER ID DE ALUMNO
        try {  //ESTE FUNCIONA


            val url = "${resources.getString(R.string.wsm)}/verId.php?no=$usr"

            //val params = HashMap<String,String>()
            //params.put("no",usr)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")

                    val jid = JSONObject(output[0].toString())
                    val idd = jid.getString("id")
                   println("Valor obtenido: $idd")

                    //asignar materias
                    try {

                        val url2 = "${resources.getString(R.string.wsm)}/asignarA.php"
                        println("url2: $url2")

                        val params2 = HashMap<String,String>()
                         params2.put("id",idd)
                         params2.put("sem",sem)

                        object: MyUtils(){
                            override fun formatResponse(response: String) {
                                //val json = JSONObject(response)
                                //val output = json.getJSONArray("output")

                                /*if(output.getString(0).equals("Ok")){
                                    Toast.makeText(this@RegisterActivity,
                                        "Se asigno correctamente",
                                        Toast.LENGTH_LONG).show()
                                    println("Asignado correctamente")
                                }*/
                                print(response)
                                finish()
                            }
                        }.consumePost(this@RegisterActivity,url2,params2)

                    }catch (e: Exception){
                        e.printStackTrace()
                        Toast.makeText(this@RegisterActivity,"Error al asignar, intente mas tarde", Toast.LENGTH_LONG).show()
                    }



                }
            }.consumeGet(this,url)

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }

    }

   private fun registrarMaterias(){
    }
}