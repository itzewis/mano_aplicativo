package com.example.manoaplicativo.fragmentos

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.manoaplicativo.*
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.list_Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class Perfil : Fragment() {

    private lateinit var btnEditarPerfil: Button
    private lateinit var bntfoto : ImageView
    private lateinit var storageReference: StorageReference
    private lateinit var storage: FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var edtNome : TextView

    private lateinit var lista_publicacao : ArrayList<Pulicacao>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento = inflater.inflate(R.layout.fragment_perfil, container, false)

        btnEditarPerfil = fragmento.findViewById(R.id.btnEditarPerfil)
        bntfoto = fragmento.findViewById(R.id.userfoto)
        edtNome = fragmento.findViewById(R.id.userNome)
        var btnConfi = fragmento.findViewById<ImageView>(R.id.btnConfig)


        recyclerView = fragmento.findViewById(R.id.areaPublicacao)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)



        lista_publicacao = arrayListOf<Pulicacao>()


        btnConfi.setOnClickListener {

            var intent = Intent(context, configuracao::class.java)
            startActivity(intent)

        }


        btnEditarPerfil.setOnClickListener {

            val intent = Intent(context,EditarPerfil::class.java)
            startActivity(intent)

        }




        mostrarDados()
        pegarDados()

        return fragmento
    }


   private fun mostrarDados() {

       val uId = FirebaseAuth.getInstance().currentUser!!.uid

       dbRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uId)

       dbRef.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {

                //nome do usuario na tela perfil


           }

           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
           }

       })

    }



    private fun pegarDados() {

        recyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes")

        val uId = FirebaseAuth.getInstance().currentUser!!.uid

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (publiSnap in snapshot.children) {

                        val dados = publiSnap.getValue(Pulicacao::class.java)
                        if (dados != null) {
                            if(uId == dados.nomeUsuario)

                                lista_publicacao.add(dados!!)

                        }


                    }

                    val mAdapter = list_Adapter(lista_publicacao)
                    recyclerView.adapter = mAdapter

                    //clicar sobre um publicacao
                    mAdapter.setOnClickListener(object : list_Adapter.onItemClickListener{
                        override fun itemClick(position: Int) {


                            //para expandir e ve a publicaco completa e poder
                            //entrar em contato o a pessoa que oferta o serviço
                            val intent = Intent(context, excluir_publicacao::class.java)

                            intent.putExtra("pubId", lista_publicacao[position].pubId)
                            intent.putExtra("descricao", lista_publicacao[position].descricao)
                            intent.putExtra("titulo", lista_publicacao[position].titulo)
                            intent.putExtra("valor", lista_publicacao[position].valor)
                            intent.putExtra("uId", lista_publicacao[position].nomeUsuario)


                            startActivity(intent)


                        }
                    })


                    recyclerView.visibility = View.VISIBLE
                    

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ops, algo deu errado", Toast.LENGTH_SHORT).show()
            }

        })

    }




}