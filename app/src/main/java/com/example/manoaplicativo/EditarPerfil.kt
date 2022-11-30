package com.example.manoaplicativo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.manoaplicativo.adapter.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class EditarPerfil : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storage : FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var imgUrl : Uri
    private lateinit var email : TextView
    private lateinit var nome : TextView

    private lateinit var imagem : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)



        dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")

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
            mudarFoto()
        }

        mostrarDados()

    }

    private fun mudarFoto() {

            val uId = FirebaseAuth.getInstance().currentUser!!.uid

            val referencia = storage.reference.child("imagens").child(uId)
            referencia.putFile(imgUrl).addOnCompleteListener{
                if(it.isSuccessful){
                    referencia.downloadUrl.addOnSuccessListener { task ->

                        atualizarRealtime(imgUrl)

                    }
                }

            }


    }

    private fun atualizarRealtime(imgUrl: Uri) {

        var uid = FirebaseAuth.getInstance().currentUser?.uid!!

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)
        var usuario = Usuario(email.toString(),uid,imgUrl.toString(),nome.toString(),senha = null)
        ref.setValue(imgUrl)

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



    private fun mostrarDados() {

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

                    Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(
                        this@EditarPerfil.imagem
                    )

                }

            }

            override fun onCancelled(error: DatabaseError) {
                email.setError("Algo deu errado")
                nome.setError("Algo deu errado")
            }


        })


    }




}