package mx.edu.itm.mark.tap_u4_proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class DatosActivity : AppCompatActivity() {
    private lateinit var btnNueva: Button
    private lateinit var editNombre: EditText
    private lateinit var editNoControl: EditText
    private lateinit var editSemestre: EditText
    private lateinit var editPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        editNoControl = findViewById(R.id.editNoControl)
        editNombre = findViewById(R.id.editNombre)
        editSemestre = findViewById(R.id.editSemestre)
        editPass = findViewById(R.id.editPass)
        btnNueva = findViewById(R.id.btnNueva)


        btnNueva.setOnClickListener{ cambiarPass() }
    }

    private fun cambiarPass(){
        if (editPass.text.isEmpty()){
            editPass.setError("La contraseña no debe estar vacia")
        }
    }
}