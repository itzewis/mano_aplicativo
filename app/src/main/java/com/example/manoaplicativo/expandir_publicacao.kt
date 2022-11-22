package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.solicitacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class expandir_publicacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandir_publicacao)

        val btnInteresse = findViewById<Button>(R.id.btnInteresse)

        btnInteresse.setOnClickListener {

            enviarSolicitacao()

        }

        setValues()

    }


    //para enviar uma solicitacao para a pessoa que fez a publicaco

    //ESSA AQUIIIIII G HBHJBHJBJ
    private fun enviarSolicitacao() {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        var dbRef = FirebaseDatabase.getInstance().getReference("Solicitacao")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                    if(uid != null){


                        val usuario = snapshot.getValue(Usuario::class.java)!!

                        val nomeCliente = usuario.nome
                        val nomePrestador = intent.getStringExtra("uId")
                        val valor = intent.getStringExtra("valor")
                        val solId = dbRef.push().key!!
                        val titulo = intent.getStringExtra("titulo")

                        val solicitacao = solicitacao(nomeCliente, nomePrestador, titulo, solId, valor)


                        dbRef.child(solId).setValue(solicitacao).addOnCompleteListener {

                            Toast.makeText(this@expandir_publicacao, "Solicitação enviada", Toast.LENGTH_SHORT).show()

                        }


                    }




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })



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