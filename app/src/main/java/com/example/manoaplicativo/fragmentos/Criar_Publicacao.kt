package com.example.manoaplicativo.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView


class Criar_Publicacao : Fragment() {


    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var titulo : EditText
    private lateinit var descricao : EditText
    private lateinit var valor : EditText
    private lateinit var criarPublicacao : Button
    private lateinit var foto : CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragmento = inflater.inflate(R.layout.fragment_criar__publicacao, container, false)

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        dbRef =  FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)


        // linha abaixo é  usada para pegar  a referencia do banco de dados firebase.
        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes/");


        titulo = fragmento.findViewById(R.id.edtTitulo)
        descricao = fragmento.findViewById(R.id.edtDescricao)
        valor = fragmento.findViewById(R.id.valor)
        criarPublicacao = fragmento.findViewById(R.id.btnCriarPuli)
        
        criarPublicacao.setOnClickListener {

            val titulo = titulo.text.toString()
            val descricao = descricao.text.toString()
            val valor = valor.text.toString()

            if(titulo.isEmpty() || valor.isEmpty() || descricao.isEmpty()){
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()

            }
            else{
                salvarDados()
            }
        }


        return fragmento
    }

    private fun salvarDados() {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue<Usuario>(Usuario::class.java)!!
                val pubId = dbRef.push().key
                val nome = user.nome.toString()
                val titulo = titulo.text.toString()
                val descricao = descricao.text.toString()
                val valor = valor.text.toString()
                val imagem = snapshot.child("imgUrl").getValue().toString()



                val publicacoes = Pulicacao(descricao,imagem,pubId,titulo,nome,uid,valor)

                if(pubId != null) {
                    dbRef.child(pubId).setValue(publicacoes)
                        .addOnCompleteListener {

                            Toast.makeText(context, "Publicação Criada", Toast.LENGTH_SHORT).show()

                        }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


}