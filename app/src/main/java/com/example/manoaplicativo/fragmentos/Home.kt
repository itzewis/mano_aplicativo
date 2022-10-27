package com.example.manoaplicativo.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.list_Adapter


class Home : Fragment() {

    private lateinit var adapter: list_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var publiArrayList: ArrayList<Publicacao>

    lateinit var imagUsuario : ArrayList<Int>
    lateinit var nome : ArrayList<String>
    lateinit var descricao : ArrayList<String>
    lateinit var titulo : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostarLista()

        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.areaPublicacao)
        recyclerView.layoutManager = layoutManager
        //adapter = list_Adapter(publiArrayList)

    }


    private fun mostarLista() {



    }


}