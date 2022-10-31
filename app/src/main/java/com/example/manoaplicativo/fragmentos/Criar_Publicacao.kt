package com.example.manoaplicativo.fragmentos

import android.content.Intent
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
import com.example.manoaplicativo.databinding.FragmentCriarPublicacaoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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


        database = FirebaseDatabase.getInstance();

        // linha abaixo é  usada para pegar  a referencia do banco de dados firebase.
        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes/");


        titulo = fragmento.findViewById(R.id.edtTitulo)
        descricao = fragmento.findViewById(R.id.edtDescricao)
        valor = fragmento.findViewById(R.id.valor)
        criarPublicacao = fragmento.findViewById(R.id.btnCriarPuli)
        
        criarPublicacao.setOnClickListener {

            var titulo = titulo.text.toString()
            var descricao = descricao.text.toString()
            var valor = valor.text.toString()

            if(titulo.isEmpty() || valor.isEmpty() || descricao.isEmpty()){
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()

            }
            else{
                salvarDados()
                val intent = Intent(context,Home::class.java)
                startActivity(intent)
            }
        }


        return fragmento
    }

    private fun salvarDados() {
        val pubId = dbRef.push().key
        val uId = FirebaseAuth.getInstance().currentUser!!.uid
        val titulo = titulo.text.toString()
        val descricao = descricao.text.toString()
        val valor = valor.text.toString()


        val publicacoes = Pulicacao(descricao,pubId,titulo,uId,valor)

        if(pubId != null) {
            dbRef.child(pubId).setValue(publicacoes)
                .addOnCompleteListener {
                    Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show()
                }

        }

    }


}