package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class pagamento_Pix : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento_pix)

        val btnCopiar = findViewById<Button>(R.id.btncopiar)

        btnCopiar.setOnClickListener {

            Toast.makeText(this, "Código Copiado", Toast.LENGTH_SHORT).show()

        }

    }
}