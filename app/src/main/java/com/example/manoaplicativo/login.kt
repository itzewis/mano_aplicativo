package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var cadastro: TextView
    private lateinit var btnEsqueci: TextView
    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.editEmail)
        editSenha = findViewById(R.id.editSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        cadastro = findViewById(R.id.cadastro)
        btnEsqueci = findViewById(R.id.btnEsqueceusuaSenha)

        btnEsqueci.setOnClickListener {
            val intent = Intent(this, esqueciaSenha::class.java)
            startActivity(intent)
        }

        cadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        btnEntrar.setOnClickListener {
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()

            //condições
            if (senha.isNotBlank() && email.isNotEmpty()){
                entrar(email, senha);
            }

            if(senha.length < 6){
                editSenha.setError("Minimo 6 caracteres")
            }

            if(senha.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun entrar(email: String, senha: String){
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@login, MainActivity::class.java)
                    finish()
                    Toast.makeText(this, "Carregando...", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@login, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
                }
            }

    }
}