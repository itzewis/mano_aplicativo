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
import com.example.manoaplicativo.EditarPerfil
import com.example.manoaplicativo.R
import com.example.manoaplicativo.adapter.Pulicacao
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.adapter.list_Adapter
import com.example.manoaplicativo.databinding.FragmentPerfilBinding
import com.example.manoaplicativo.perfil_Avaliacao
import com.example.manoaplicativo.perfil_Publicacao
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
    private lateinit var btnPostagens : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmento = inflater.inflate(R.layout.fragment_perfil, container, false)

        btnEditarPerfil = fragmento.findViewById(R.id.btnEditarPerfil)
        bntfoto = fragmento.findViewById(R.id.userfoto)
        edtNome = fragmento.findViewById(R.id.userNome)
        btnPostagens = fragmento.findViewById(R.id.publicacoes)


        btnEditarPerfil.setOnClickListener {

            val intent = Intent(context,EditarPerfil::class.java)
            startActivity(intent)

        }


        btnPostagens.setOnClickListener {

            val intent = Intent(context,perfil_Publicacao::class.java)
            startActivity(intent)

        }

        mostrarDados()


        return fragmento
    }


   private fun mostrarDados() {

       var uId = FirebaseAuth.getInstance().currentUser!!.uid

       dbRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uId)

       dbRef.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {



           }

           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
           }

       })

    }





}