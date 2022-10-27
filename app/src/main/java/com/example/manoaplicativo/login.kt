package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class login : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var cadastro: TextView
    private lateinit var btnEsqueci: TextView
    //private lateinit var mAuth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        //mAuth = FirebaseAuth.getInstance()

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


    }
}