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
        var database = FirebaseDatabase.getInstance().getReference("Solicitacao")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var user = snapshot.getValue(Usuario::class.java)!!
                var pub = snapshot.getValue<Pulicacao>(Pulicacao::class.java)!!

                val nomeCliente = user.nome.toString()
                val nomePrestador = pub.nomeUsuario.toString()
                val uIdCliente = uid
                val uIdPrestador = pub.uid
                val valor = pub.valor.toString()
                val solId = database.push().key

                val solicitacao = solicitacao(nomeCliente,nomePrestador,uIdCliente,uIdPrestador,solId,valor)

                if(solId != null){

                    database.child(solId).setValue(solicitacao).addOnCompleteListener{

                        Toast.makeText(this@expandir_publicacao, "Uma solicitação foi enviada", Toast.LENGTH_SHORT).show()

                    }

                        .addOnFailureListener {

                            Toast.makeText(this@expandir_publicacao, "Erro ao enviar solicitação", Toast.LENGTH_SHORT).show()

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