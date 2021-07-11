package mx.edu.itm.mark.tap_u4_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mx.edu.itm.mark.tap_u4_proyecto.models.Alumno
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var editLoginUsr: EditText
    private lateinit var editLoginPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editLoginUsr = findViewById(R.id.editLoginUsr)
        editLoginPass = findViewById(R.id.editLoginPass)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener { login() }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        try {
            if(editLoginUsr.text.isEmpty()){
                editLoginUsr.setError("El usuario no debe ser vacio ")
                return
            }
            if(editLoginPass.text.isEmpty()){
                editLoginPass.error = "La contraseña no debe ser vacia"
                return
            }

            val usr = editLoginUsr.text.toString()
            val pass = editLoginPass.text.toString()

            val url = "${resources.getString(R.string.wsm)}/acceso.php"
            //val urld = "${"http://192.168.1.70:80/my_sge/"}/acceso.php"

            val params = HashMap<String,String>()
            params.put("usr",usr)
            params.put("pass",pass)

            object: MyUtils(){
                override fun formatResponse(response: String) {
                    val json = JSONObject(response)
                    val output = json.getJSONArray("output")

                    val jsonAlumno = output.getJSONObject(0)

                   val alumno = Alumno(
                       jsonAlumno.getInt("id"),
                       jsonAlumno.getString("n_control"),
                       jsonAlumno.getString("nombre"),
                       jsonAlumno.getInt("semestre")
                   )
                    //val alumno = Gson().fromJson(jsonAlumno.toString(),Alumno::class.java)

                    val intent = Intent(this@MainActivity, MenuActivity::class.java)
                    intent.putExtra("alumno",alumno)
                    startActivity(intent)
                    finish()
                    println("Alumno: $jsonAlumno")
                }

            }.consumePost(this,url,params)
        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()

        }

    }
}