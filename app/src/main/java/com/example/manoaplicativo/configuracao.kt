package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class configuracao : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)


        val btnExcluir = findViewById<TextView>(R.id.btnExcluirConta)
        var btnSair = findViewById<TextView>(R.id.btnSair)


        btnSair.setOnClickListener {


            val mAuth = FirebaseAuth.getInstance()
            try {

                //alertDialog
                val builder = AlertDialog.Builder(this)
                //mostrar mensagem
                builder.setMessage(R.string.dialogMessage)


                //caso clique no sim
                builder.setPositiveButton("Sim"){dialogInterface, which ->

                    finish()


                }
                //caso clique em não ira voltar para onde estava
                builder.setNeutralButton("Cancelar"){dialogInterface , which ->

                }


                val alertDialog: AlertDialog = builder.create()
                //outras proriedades do alertDialog
                alertDialog.setCancelable(false)
                alertDialog.show()



            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "onClick: Exception " + e.message, e)
            }


        }


        btnExcluir.setOnClickListener {

            val intent = Intent(this,confirmacaoExcluirConta::class.java)
            startActivity(intent)


        }


    }

}