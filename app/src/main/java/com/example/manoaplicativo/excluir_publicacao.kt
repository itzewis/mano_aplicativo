package com.example.manoaplicativo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.manoaplicativo.adapter.Pulicacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class excluir_publicacao : AppCompatActivity() {

    private lateinit var btnExcluirPubli : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excluir_publicacao)

        setValues()

       btnExcluirPubli = findViewById(R.id.btnExcluirPubli)


        btnExcluirPubli.setOnClickListener {

            excluirPublicacao()

        }

    }

    private fun excluirPublicacao() {

    //deletar publicacoa

    }


    private fun setValues(){

        val nome = findViewById<TextView>(R.id.nome)
        val valor = findViewById<TextView>(R.id.valor)
        val titulo = findViewById<TextView>(R.id.titulo)
        val descricao = findViewById<TextView>(R.id.descricao)


        nome.text = intent.getStringExtra("uId")
        valor.text = intent.getStringExtra("valor")
        titulo.text = intent.getStringExtra("titulo")
        descricao.text = intent.getStringExtra("descricao")

    }


}