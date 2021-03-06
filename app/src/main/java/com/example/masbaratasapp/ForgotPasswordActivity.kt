package com.example.masbaratasapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        txtEmail = findViewById(R.id.txtCorreo)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
    }


    fun recuperarPass(view: View){
        val email = txtEmail.text.toString()

        if (!TextUtils.isEmpty(email)){
            progressBar.visibility= View.VISIBLE
            /*enviamos un correo para el email registrado para cambiar la contraseña*/
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    /*si la accion fue exitosa llevamos al usuario a la vista login*/
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    Toast.makeText(this, "Error al enviar el email", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}