package com.example.manoaplicativo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.R

class list_Adapter (private val list_Adapter: Array<Pulicacao>) :
    RecyclerView.Adapter<list_Adapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemAtual = list_Adapter[position]

        holder.descricao.text = itemAtual.descricao
        holder.titulo.text = itemAtual.titulo
        holder.nomeUsuario.text = itemAtual.nome


    }

    override fun getItemCount(): Int {

        return  list_Adapter.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nomeUsuario : TextView = itemView.findViewById(R.id.nomeUsuario)
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val descricao : TextView = itemView.findViewById(R.id.descricao)
        val imgUsuario : ImageView = itemView.findViewById(R.id.imgUsuario)


    }


}