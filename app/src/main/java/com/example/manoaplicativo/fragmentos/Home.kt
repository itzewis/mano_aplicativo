package com.example.manoaplicativo.fragmentos

import android.graphics.Insets.add
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.list_Adapter
import com.example.manoaplicativo.databinding.FragmentCriarPublicacaoBinding
import com.google.android.gms.common.util.WorkSourceUtil.add
import com.google.firebase.auth.FirebaseAuth
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = fragmento.findViewById(R.id.areaPublicacao)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        lista_publicacao = arrayListOf<Pulicacao>()

        pegarDados()

        return fragmento
    }

    private fun pegarDados() {

        recyclerView.visibility = View.GONE



        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {

                    if (snapshot.exists()) {
                        for (publiSnap in snapshot.children) {
                            val dados = publiSnap.getValue(Pulicacao::class.java)
                            lista_publicacao.add(dados!!)
                        }
                        val mAdapter = list_Adapter(lista_publicacao)
                        recyclerView.adapter = mAdapter

                        recyclerView.visibility = View.VISIBLE

                    }

                } catch (ex: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}

