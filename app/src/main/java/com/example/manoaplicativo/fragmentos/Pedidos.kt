package com.example.manoaplicativo.fragmentos

import android.annotation.SuppressLint
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

                        val dados = publiSnap.getValue(solicitacao::class.java)
                        lista_pedidos.add(dados!!)


                    }

                    val mAdapter = list_pedidos(lista_pedidos)
                    recyclerView.adapter = mAdapter

                    recyclerView.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ops, algo deu errado", Toast.LENGTH_SHORT).show()
            }

        })

    }
}