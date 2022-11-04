package com.example.manoaplicativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class formaPagamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_pagamento)

        var debito = findViewById<TextView>(R.id.cartaoDebito)
        var credito = findViewById<TextView>(R.id.cartaoCredito)
        var pix = findViewById<TextView>(R.id.pix)


        debito.setOnClickListener {

            intent = Intent(this,pagamento_Debito::class.java)
            startActivity(intent)

        }

        credito.setOnClickListener {

            intent = Intent(this,pagamento_Credito::class.java)
            startActivity(intent)

        }

        pix.setOnClickListener {

            intent = Intent(this,pagamento_Pix::class.java)
            startActivity(intent)

        }


    }
}