package com.example.manoaplicativo.adapter

import com.google.firebase.database.DatabaseReference


data class Pulicacao(

    var descricao: String? = null,
    var pubId: String? = null,
    var titulo: String? = null,
    var nomeUsuario: String? = null,
    var valor: String? = null

)