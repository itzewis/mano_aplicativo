package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class formaPagamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_pagamento)

        var debito = findViewById<TextView>(R.id.cartaoDebito)


    }
}