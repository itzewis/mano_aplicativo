package com.example.manoaplicativo

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.databinding.ActivityEditarPerfilBinding
import com.example.manoaplicativo.fragmentos.Perfil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.EventListener

class EditarPerfil : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storage : StorageReference
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var imgUrl : Uri


    private lateinit var usuario : Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)



        dbRef = FirebaseDatabase.getInstance().getReference()

        var btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        var btnFoto = findViewById<ImageView>(R.id.btnFoto)
        var salvarAlteracoes = findViewById<Button>(R.id.salvarAlteracoes)
        var nome = findViewById<TextView>(R.id.nome)
        var email = findViewById<TextView>(R.id.email)


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

    }


    //para que a imagem escolhida apareca la no campo btnCamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data != null){
                imgUrl = data.data!!
                var btnFoto = findViewById<ImageView>(R.id.btnFoto)
                btnFoto.setImageURI(imgUrl)
            }
        }

    }
}