package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class configuracao : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)


        val btnExcluir = findViewById<TextView>(R.id.btnExcluirConta)
        var btnSair = findViewById<TextView>(R.id.btnSair)


        btnExcluir.setOnClickListener {

            val intent = Intent(this,confirmacaoExcluirConta::class.java)
            startActivity(intent)

        }


    }

}