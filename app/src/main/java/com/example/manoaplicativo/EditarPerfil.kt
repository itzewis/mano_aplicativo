package com.example.manoaplicativo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.fragmentos.Perfil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.InputStream


class EditarPerfil : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storage : StorageReference
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var imgUrl : Uri
    private lateinit var email : TextView
    private lateinit var nome : TextView

    private lateinit var imagem : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)



        dbRef = FirebaseDatabase.getInstance().getReference()

        imagem= findViewById<ImageView>(R.id.btnFoto)
        val salvarAlteracoes = findViewById<Button>(R.id.salvarAlteracoes)
        nome = findViewById(R.id.nome)
        email = findViewById(R.id.email)


        imagem.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)

        }

        salvarAlteracoes.setOnClickListener {
            Toast.makeText(this, "Imagem Alterada", Toast.LENGTH_SHORT).show()
        }

        mostrarDados()

    }



    //para que a imagem escolhida apareca la no campo btnCamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var uid = FirebaseAuth.getInstance().uid


        if(data != null){
            if(data.data != null){
                imgUrl = data.data!!
                val btnFoto = findViewById<ImageView>(R.id.btnFoto)
                btnFoto.setImageURI(imgUrl)
            }
        }

    }



    private fun mostrarDados(){

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                        val user = snapshot.getValue<Usuario>(Usuario::class.java)!!

                            email.text = user.email
                            nome.text = user.nome

                //vai pegar o link que esta localizado dentro do imgUrl
                //transfromar em string e o Picasso vai passar esse valor
                //para a imagemview
                //caso de erro vai aparecer uma imagem toda verde
                val image = snapshot.child("imgUrl").getValue().toString()

                if (!image.equals("default")) {

                    Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(imagem);

                }

            }

            override fun onCancelled(error: DatabaseError) {
                email.setError("Algo deu errado")
                nome.setError("Algo deu errado")
            }


        })


    }




}