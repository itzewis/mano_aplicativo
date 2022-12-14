package com.example.manoaplicativo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.list_Adapter
import com.example.manoaplicativo.adapter.solicitacao
import com.example.manoaplicativo.fragmentos.Publicacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class expandir_publicacao : AppCompatActivity() {

    private lateinit var lista_publicacao : ArrayList<Pulicacao>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandir_publicacao)

        val btnInteresse = findViewById<Button>(R.id.btnInteresse)
        val btnCaht = findViewById<Button>(R.id.btnChat)

        btnCaht.setOnClickListener {

           val dbRef= FirebaseDatabase.getInstance().getReference("Usuarios")
            val uid = FirebaseAuth.getInstance().currentUser?.uid!!

            dbRef.child(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val usuario = snapshot.getValue(Usuario::class.java)

                    val numero = usuario?.numero

                    val url = "https:/api.whatsapp.com/send?phone=$numero"
                    val it = Intent(Intent.ACTION_VIEW)
                    it.setData(Uri.parse(url))
                    startActivity(it)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })

        }

        btnInteresse.setOnClickListener {

            enviarSolicitacao()

        }

        setValues()

    }


    //para enviar uma solicitacao para a pessoa que fez a publicaco

    //ESSA AQUIIIIII G HBHJBHJBJ
    private fun enviarSolicitacao() {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val dbRef = FirebaseDatabase.getInstance().getReference("Solicitacao")
        var refe = FirebaseDatabase.getInstance().getReference("Publicacoes")



        refe.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                        val usuario = snapshot.getValue(Usuario::class.java)!!
                        val public = snapshot.getValue(Pulicacao::class.java)!!

                        val nomePrestador = intent.getStringExtra("uId")
                        val valor = intent.getStringExtra("valor")
                        val pubId = dbRef.push().key
                        var uidCliente = uid
                        val titulo = intent.getStringExtra("titulo")

                        val solicitacao = solicitacao(nomePrestador, pubId, titulo,uidCliente, valor)



                if(pubId != null) {
                    dbRef.child(pubId).setValue(solicitacao)
                        .addOnCompleteListener {

                            Toast.makeText(this@expandir_publicacao, "Solicita????o enviada para $nomePrestador", Toast.LENGTH_SHORT).show()

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
        val pubid = findViewById<TextView>(R.id.uid)

        pubid.text = intent.getStringExtra("pubId")
        nome.text = intent.getStringExtra("uId")
        valor.text = intent.getStringExtra("valor")
        titulo.text = intent.getStringExtra("titulo")
        descricao.text = intent.getStringExtra("descricao")

    }

}