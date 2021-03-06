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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtApellido:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtPassword:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtEmail = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPass)

        progressBar = findViewById(R.id.progressBar)

        /*instancia de la BD*/
        database = FirebaseDatabase.getInstance()
        /*instancia para la  autenticacion*/
        auth = FirebaseAuth.getInstance()

        /*referencia para leer o escribir en una ubicacion*/
        dbReference = database.reference.child("User")
    }


    /*metodo de registro ligado al boton*/
    fun registrar(view: View){
        nuevaCuenta()
    }

    private fun nuevaCuenta(){
        val nombre:String=txtNombre.text.toString()
        val apellido:String = txtApellido.text.toString()
        val email:String = txtEmail.text.toString()
        val password:String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(email)
            && !TextUtils.isEmpty(password)){

            progressBar.visibility= View.VISIBLE
            /*creaion de usuario con email y contraseña, una vez echo esto verificamos que la tarea o la
            * accion se haya cumplido*/
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
                if (task.isComplete){
                    /*obtenemos el usuario registrado*/
                    val user: FirebaseUser? = auth.currentUser
                    verifyEmail(user)

                    /*agregamos la ubicacion para añadir los demas datos del usuario*/
                    val userBD = dbReference.child(user?.uid.toString())

                    /*almacenamos nombre y apellido en la instancia de la bd*/
                    userBD.child("Nombre").setValue(nombre)
                    userBD.child("Apellido").setValue(apellido)
                    toLogin()
                }
            }

        }
    }


    private fun verifyEmail(user: FirebaseUser?){
        /*le enviamos un correo al usuario de verificacion*/
        user?.sendEmailVerification()?.addOnCompleteListener(this){ task ->
            /*verificamos que la accion se haya completado*/
            if (task.isComplete){
                Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Error al enviar email", Toast.LENGTH_LONG).show()

            }
        }
    }


    private fun toLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}