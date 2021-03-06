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

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPass)

        progressBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()
    }


    fun login(view: View){
        loginUser()
    }


    private fun loginUser(){
        val user:String = txtEmail.text.toString()
        val pass:String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            progressBar.visibility= View.VISIBLE
            /*logueamos al usuario y validamos si se cumplio la accion*/
            auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this){ task ->
                /*si todo esta bien lo llevamos a la vista principal*/
                if (task.isSuccessful){
                    toMain()
                }else{
                    Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun toMain(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    /*acciones de boton que me llevan a la vista de registro*/
    fun toRegistro(view:View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    /*acciones de boton que me llevan a la vista de olvidar contraseña*/
    fun olvidastePass(view:View){
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }
}