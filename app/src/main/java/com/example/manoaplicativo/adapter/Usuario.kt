package com.example.manoaplicativo.adapter

import android.text.Editable
import com.google.firebase.database.Exclude

data class Usuario(
    val email: String? = null,
    val id : String? = null,
    val imgUrl: String? = null,
    //val latitude : String,
    //val longitude : String,
    val nome: String? = null,
    val senha:String? = null,
    var tipoUsuario : String = "Cliente"

)



