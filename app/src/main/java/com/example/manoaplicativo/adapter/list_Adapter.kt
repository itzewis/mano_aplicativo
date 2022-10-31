package com.example.manoaplicativo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R


class list_Adapter(private val list_Adapter: ArrayList<Pulicacao>) :
    RecyclerView.Adapter<list_Adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemAtual = list_Adapter[position]

        holder.descricao.text = itemAtual.descricao
        holder.titulo.text = itemAtual.titulo
        holder.valor.text = itemAtual.valor
        //holder.nomeUsuario.text = itemAtual.nome
        //holder.imgUsuario.setImageURI(Uri? =uri) = itemAtual.imgUsuario

    }

    override fun getItemCount(): Int {

        return  list_Adapter.size

    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nomeUsuario : TextView = itemView.findViewById(R.id.uNome)
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val descricao : TextView = itemView.findViewById(R.id.descricao)
        //val imgUsuario : ImageView = itemView.findViewById(R.id.uFoto)
        val valor : TextView = itemView.findViewById(R.id.valor)


    }


}