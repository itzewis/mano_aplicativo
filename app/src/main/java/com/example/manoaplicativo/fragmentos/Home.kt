package com.example.manoaplicativo.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.list_Adapter
import com.example.manoaplicativo.databinding.FragmentCriarPublicacaoBinding
import com.example.manoaplicativo.expandir_publicacao
import com.example.manoaplicativo.list_publicacao
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class Home : Fragment() {

    private lateinit var binding: FragmentCriarPublicacaoBinding

    private lateinit var storage : FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference

    private lateinit var lista_publicacao : ArrayList<Pulicacao>
    private lateinit var recyclerView: RecyclerView

    lateinit var valor : ArrayList<String>
    lateinit var descricao : ArrayList<String>
    lateinit var titulo : ArrayList<String>
    lateinit var nome : TextView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = fragmento.findViewById(R.id.areaPublicacao)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        progressBar = fragmento.findViewById(R.id.carregando)


        lista_publicacao = arrayListOf<Pulicacao>()

        pegarDados()



        return fragmento
    }

    private fun pegarDados() {

        recyclerView.visibility = View.GONE


        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (publiSnap in snapshot.children) {

                            val dados = publiSnap.getValue(Pulicacao::class.java)
                            lista_publicacao.add(dados!!)
                            mostrarProgress()
                        }

                        val mAdapter = list_Adapter(lista_publicacao)
                        recyclerView.adapter = mAdapter

                        mAdapter.setOnClickListener(object : list_Adapter.onItemClickListener{
                            override fun itemClick(position: Int) {

                                //para expandir e ve a publicaco completa e poder
                                //entrar em contato o a pessoa que oferta o serviço
                                val intent = Intent(context, expandir_publicacao::class.java)
                                startActivity(intent)

                            }
                        })


                        recyclerView.visibility = View.VISIBLE
                        esconderProgress()

            }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ops, algo deu errado", Toast.LENGTH_SHORT).show()
            }

        })

    }


    //para mostrar o progressbar
    private fun mostrarProgress(){

        progressBar.visibility = View.VISIBLE

    }

    private fun esconderProgress(){

        progressBar.visibility = View.INVISIBLE

    }


}

