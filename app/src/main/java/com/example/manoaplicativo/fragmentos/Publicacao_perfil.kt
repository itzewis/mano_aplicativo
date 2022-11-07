package com.example.manoaplicativo.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.manoaplicativo.R


class Publicacao_perfil : Fragment(R.layout.fragment_perfil__publicacao) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragmento = inflater.inflate(R.layout.fragment_publicacao_perfil, container, false)



        return fragmento
    }

}