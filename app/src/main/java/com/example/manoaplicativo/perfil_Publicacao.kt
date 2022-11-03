package com.example.manoaplicativo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class perfil_Publicacao : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_perfil__publicacao, container, false)




        return fragmento
    }


}