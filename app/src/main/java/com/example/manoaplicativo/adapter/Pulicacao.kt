package com.example.manoaplicativo.adapter

import com.google.firebase.database.DatabaseReference


data class Pulicacao(

    var descricao: String? = null,
    //imgUrl : String? = null,
    var pubId: String? = null,
    var titulo: String? = null,
    var uId: String? = null,
    var valor: String? = null

)