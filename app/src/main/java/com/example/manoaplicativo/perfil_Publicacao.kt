package com.example.manoaplicativo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.list_Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class perfil_Publicacao : Fragment() {

    private lateinit var dbRef : DatabaseReference


    private lateinit var lista_publicacao : ArrayList<Pulicacao>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento =  inflater.inflate(R.layout.fragment_perfil__publicacao, container, false)


        recyclerView = fragmento.findViewById(R.id.framePerfil)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)




        lista_publicacao = arrayListOf<Pulicacao>()

        pegarDados()



        return fragmento
    }




    private fun pegarDados() {

        recyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Publicacoes")

        var uId = FirebaseAuth.getInstance().currentUser

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

               if(uId != uId){


                   if (snapshot.exists()) {
                       for (publiSnap in snapshot.children) {

                           val dados = publiSnap.getValue(Pulicacao::class.java)
                           lista_publicacao.add(dados!!)

                       }

                       val mAdapter = list_Adapter(lista_publicacao)
                       recyclerView.adapter = mAdapter

                       //clicar sobre um publicacao
                       mAdapter.setOnClickListener(object : list_Adapter.onItemClickListener{
                           override fun itemClick(position: Int) {


                               //para expandir e ve a publicaco completa e poder
                               //entrar em contato o a pessoa que oferta o serviço
                               val intent = Intent(context, expandir_publicacao::class.java)

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

               }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Ops, algo deu errado", Toast.LENGTH_SHORT).show()
            }

        })

    }

}