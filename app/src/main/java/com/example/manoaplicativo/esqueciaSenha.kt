package com.example.manoaplicativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class esqueciaSenha : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esquecia_senha)

        mAuth = FirebaseAuth.getInstance()

        val btnVoltarLogin = findViewById<TextView>(R.id.btnVoltarLogin)
        val btnConfirmarEmail = findViewById<Button>(R.id.btnConfirmarEmail)

        btnVoltarLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }

        btnConfirmarEmail.setOnClickListener {
            val edtemail = findViewById<EditText>(R.id.editEmailRecuperarSenha)
            val email = edtemail.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Insira um email válido", Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Email enviado!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Nenhum usuário foi encontrado!", Toast.LENGTH_SHORT).show()
                        }

                    }
            }

        }


    }
}