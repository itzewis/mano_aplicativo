package com.example.manoaplicativo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R
import java.net.URL


class list_Adapter(private val list_publicacao: ArrayList<Pulicacao>) :
    RecyclerView.Adapter<list_Adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_list_publicacao,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemAtual = list_publicacao[position]

        holder.descricao.text = itemAtual.descricao
        holder.titulo.text = itemAtual.titulo
        holder.valor.text = itemAtual.valor
        holder.nomeUsuario.text = itemAtual.uId
        //holder.imgUsuario.setImageURI(URL) = itemAtual.uImageUrl

    }

    override fun getItemCount(): Int {

        return  list_publicacao.size

    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nomeUsuario : TextView = itemView.findViewById(R.id.uNome)
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val descricao : TextView = itemView.findViewById(R.id.descricao)
        //val imgUsuario : ImageView = itemView.findViewById(R.id.uFoto)
        val valor : TextView = itemView.findViewById(R.id.valor)


    }


}