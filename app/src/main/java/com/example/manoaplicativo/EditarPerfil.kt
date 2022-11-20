package com.example.manoaplicativo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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


    private lateinit var usuario : Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)



        dbRef = FirebaseDatabase.getInstance().getReference()

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnFoto = findViewById<ImageView>(R.id.btnFoto)
        val salvarAlteracoes = findViewById<Button>(R.id.salvarAlteracoes)
        nome = findViewById(R.id.nome)
        email = findViewById(R.id.email)





        btnVoltar.setOnClickListener {

            val intent = Intent(this@EditarPerfil,Perfil::class.java )
            startActivity(intent)

        }

        btnFoto.setOnClickListener {
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
                val user = FirebaseAuth.getInstance()

                if (user.currentUser != null){

                    user.currentUser?.let {

                        val reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)


                        email.text = it.email
                        nome.text = it.uid





                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                email.setError("Algo deu errado")
                nome.setError("Algo deu errado")
            }


        })


    }




}