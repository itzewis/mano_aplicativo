package com.example.manoaplicativo.fragmentos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.*
import com.example.manoaplicativo.expandir_publicacao
import com.example.manoaplicativo.formaPagamento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Pedidos : Fragment() {

    private lateinit var lista_pedidos: ArrayList<solicitacao>
    private lateinit var list: ArrayList<Usuario>
    private lateinit var recyclerView: RecyclerView

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento = inflater.inflate(R.layout.fragment_pedidos, container, false)


        recyclerView = fragmento.findViewById(R.id.areaPedidos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)




        lista_pedidos = arrayListOf<solicitacao>()
        list = arrayListOf<Usuario>()

        mostrarPedidos()

        return fragmento
    }


    private fun mostrarPedidos() {

        recyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Solicitacao")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (publiSnap in snapshot.children) {

                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        val dados = publiSnap.getValue(solicitacao::class.java)

                        if (dados != null) {
                            if(uid == dados.uidCliente){

                                lista_pedidos.add(dados!!)

                            }


                        }
                    }

                    val mAdapter = list_pedidos(lista_pedidos)
                    recyclerView.adapter = mAdapter


                    mAdapter.setOnClickListener(object : list_pedidos.onItemClickListener{
                        override fun itemClick(position: Int) {


                            //para expandir e ve a publicaco completa e poder
                            //entrar em contato o a pessoa que oferta o serviço
                            val intent = Intent(context, formaPagamento::class.java)
                            startActivity(intent)


                        }
                    })



                    recyclerView.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ops, algo deu errado", Toast.LENGTH_SHORT).show()
            }

        })

    }
}