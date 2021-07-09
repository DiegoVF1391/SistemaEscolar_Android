package mx.edu.itm.mark.tap_u4_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mx.edu.itm.mark.tap_u4_proyecto.utils.MyUtils
import org.json.JSONObject
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private lateinit var editRegisterUsr: EditText
    private lateinit var editRegisterPass: EditText
    private lateinit var editRegisterName: EditText
    private lateinit var editRegisterSemester: EditText
    private lateinit var btnRegisterSave: Button
    private lateinit var btnRegisterCancel: Button

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

        try {
            val usr = editRegisterUsr.text.toString()
            val pass = editRegisterPass.text.toString()
            val nombre = editRegisterName.text.toString()
            val sem = editRegisterSemester.text.toString()

            val url = "${resources.getString(R.string.wsM)}registro.php"

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
                        finish()
                    }
                }
            }.consumePost(this,url,params)

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error, intente mas tarde", Toast.LENGTH_LONG).show()
        }
    }
}