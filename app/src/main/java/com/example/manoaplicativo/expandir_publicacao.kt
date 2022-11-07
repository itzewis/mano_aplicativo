package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class expandir_publicacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandir_publicacao)

        val btnInteresse = findViewById<Button>(R.id.btnInteresse)

        btnInteresse.setOnClickListener {

            Toast.makeText(this, "Uma solicitação foi enviada", Toast.LENGTH_SHORT).show()
            enviarSolicitacao()

        }

        setValues()

    }


    //para enviar uma solicitacao para a pessoa que fez a publicaco
    //
    private fun enviarSolicitacao() {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        var database = FirebaseDatabase.getInstance().getReference("Solicitacao")



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