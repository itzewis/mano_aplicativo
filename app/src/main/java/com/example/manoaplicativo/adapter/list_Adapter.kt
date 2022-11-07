package com.example.manoaplicativo.adapter

import android.content.Context
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.example.manoaplicativo.R
import com.example.manoaplicativo.expandir_publicacao
import de.hdodenhof.circleimageview.CircleImageView



class list_Adapter(val list_publicacao: ArrayList<Pulicacao>) :
    RecyclerView.Adapter<list_Adapter.MyViewHolder>(){

    private lateinit var clique : onItemClickListener


    interface onItemClickListener{
        fun itemClick(position: Int)
    }

    fun setOnClickListener(clickListener: onItemClickListener){
        clique = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_list_publicacao,parent,false)
        return MyViewHolder(itemView,clique)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemAtual = list_publicacao[position]


        holder.descricao.text = itemAtual.descricao
        holder.titulo.text = itemAtual.titulo
        holder.valor.text = itemAtual.valor
        holder.nomeUsuario.text = itemAtual.uId


    }

    override fun getItemCount(): Int {

        return  list_publicacao.size

    }


    class MyViewHolder(itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){



        val nomeUsuario : TextView = itemView.findViewById(R.id.uNome)
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val descricao : TextView = itemView.findViewById(R.id.descricao)
        val imgUrl : CircleImageView = itemView.findViewById(R.id.uFoto)
        val valor : TextView = itemView.findViewById(R.id.valor)

        init {
            itemView.setOnClickListener {
                clickListener.itemClick(adapterPosition)
            }

        }


    }


}