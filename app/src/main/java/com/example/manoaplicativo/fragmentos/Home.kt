package com.example.manoaplicativo.fragmentos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.list_Adapter
import com.example.manoaplicativo.databinding.FragmentCriarPublicacaoBinding
import com.example.manoaplicativo.expandir_publicacao
import com.example.manoaplicativo.login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class Home : Fragment() {

    private lateinit var binding: FragmentCriarPublicacaoBinding

    private lateinit var storage : FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference


    private lateinit var lista_publicacao : ArrayList<Pulicacao>
    private lateinit var list : ArrayList<Usuario>
    private lateinit var recyclerView: RecyclerView

    lateinit var valor : ArrayList<String>
    lateinit var descricao : ArrayList<String>
    lateinit var titulo : ArrayList<String>
    lateinit var nome : TextView
    lateinit var progressBar: ProgressBar



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_home, container, false)

        nome = fragmento.findViewById(R.id.textViewnome)

        recyclerView = fragmento.findViewById(R.id.areaPublicacao)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        progressBar = fragmento.findViewById(R.id.carregando)

        lista_publicacao = arrayListOf<Pulicacao>()
        list = arrayListOf<Usuario>()


        pegarDados()
        mostrarNome()

        return fragmento
    }



    private fun pegarDados() {

        recyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes")

        val uId = FirebaseAuth.getInstance().currentUser!!.uid

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

                        //clicar sobre um publicacao
                        mAdapter.setOnClickListener(object : list_Adapter.onItemClickListener{
                            override fun itemClick(position: Int) {


                                //para expandir e ve a publicaco completa e poder
                                //entrar em contato o a pessoa que oferta o serviço
                                val intent = Intent(context, expandir_publicacao::class.java)

                                intent.putExtra("pubId", lista_publicacao[position].pubId)
                                intent.putExtra("descricao", lista_publicacao[position].descricao)
                                intent.putExtra("titulo", lista_publicacao[position].titulo)
                                intent.putExtra("valor", lista_publicacao[position].valor)
                                intent.putExtra("uId", lista_publicacao[position].nomeUsuario)


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


    private fun mostrarNome(){

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue<Usuario>(Usuario::class.java)!!

                nome.text = user.nome

            }

            override fun onCancelled(error: DatabaseError) {
                nome.setError("Algo deu errado")
            }


        })


    }


}


