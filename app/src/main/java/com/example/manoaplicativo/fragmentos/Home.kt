package com.example.manoaplicativo.fragmentos

import android.graphics.Insets.add
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var adapitador: list_Adapter
    private lateinit var recyclerView: RecyclerView

    lateinit var valor : ArrayList<String>
    lateinit var descricao : ArrayList<String>
    lateinit var titulo : ArrayList<String>


    var list = ArrayList<list_Adapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_home, container, false)

        return fragmento
    }

}

