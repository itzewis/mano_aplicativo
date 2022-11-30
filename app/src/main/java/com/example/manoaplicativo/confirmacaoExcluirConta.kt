package com.example.manoaplicativo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class confirmacaoExcluirConta : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao_excluir_conta)


     val botaoExcluir = findViewById<Button>(R.id.btnExcluir)

        botaoExcluir.setOnClickListener {


            try {

                //alertDialog
                val builder = AlertDialog.Builder(this)
                //mostrar mensagem
                builder.setMessage(R.string.deletar)


                //caso clique no sim
                builder.setPositiveButton("Deletar"){dialogInterface, which ->

                    val user = Firebase.auth.currentUser!!
                    val mRef = FirebaseDatabase.getInstance().getReference("Usuarios")

                    user.delete().addOnCompleteListener { task ->

                        if(task.isSuccessful){

                            val i = Intent(this,login::class.java)
                            i.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(i)
                            finish()
                            Toast.makeText(this, "Sua conta foi deletada", Toast.LENGTH_SHORT).show()

                        }

                    }


                    Toast.makeText(this, "Conta deletada", Toast.LENGTH_SHORT).show()

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



    }



}