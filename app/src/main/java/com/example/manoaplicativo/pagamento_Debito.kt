package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class pagamento_Debito : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento_debito)

        var btnpagar = findViewById<Button>(R.id.btnPagar2)

        btnpagar.setOnClickListener {


            try {

                //alertDialog
                val builder = AlertDialog.Builder(this)
                //mostrar mensagem
                builder.setMessage(R.string.pagamento)
                builder.setTitle("Pagamento")


                //caso clique no sim
                builder.setPositiveButton("OK"){dialogInterface, which ->

                    finish()


                }

                val alertDialog: AlertDialog = builder.create()
                //outras proriedades do alertDialog
                alertDialog.setCancelable(false)
                alertDialog.show()



            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "onClick: Exception " + e.message, e)
            }


        }
    }
}