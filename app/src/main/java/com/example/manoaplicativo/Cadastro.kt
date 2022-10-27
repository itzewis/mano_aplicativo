package com.example.manoaplicativo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage : StorageReference
    private lateinit var database : DatabaseReference
    private lateinit var imgUri : Uri
    val imagem = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.editEmailC.text.toString()
        val senha = binding.editSenhaC.text.toString()


        database = FirebaseDatabase.getInstance().getReference("Usuarios")
        storage = FirebaseStorage.getInstance().getReference().child("Foto")
        auth = FirebaseAuth.getInstance()


        //inicio de uma nova activity
        //da tela de cadastro para a de login
        binding.textLogin.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }


        //"botao" camera, que disponibilizara somente arquivos png,jpg
        binding.btnCamera.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent,imagem)
        }


        binding.btnCriarConta.setOnClickListener {
            //caso algum dos edittexts estejam vazio ira aparecer uma mensangem para
            //preencher todos os campos.
            //binding vai iniciar o meu campo da onde eu vou pegar as informações do usaurio,
            //sem precisar fazer a declaração e o findviewbyid
            //e tambem ja o transforma de text para string, sem precisar fazer ou declaracao...

            if(binding.editSenhaC.text.toString().isEmpty() || binding.editEmailC.text.toString().isEmpty()
                || binding.editNomeC.text.toString().isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

            //senha com menos de 4 caracteres ira aparecer erro e uma mensagem
            if(binding.editSenhaC.text.toString().length < 6){
                binding.editSenhaC.setError("Minimo 6 caracteres")
            }

            else{
                val nome = binding.editNomeC.text.toString()
                val imgUrl = binding.btnCamera.toString()
                val uId = database.push().key!!

                uploadInfo(email,senha)
                criarConta(nome, email, imgUrl, senha,uId)
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == imagem){
            if(resultCode == RESULT_OK){
                val imageData = data!!.getData()

                val imageNome : StorageReference = storage.child("imagem" + imageData!!.lastPathSegment)
                imageNome.putFile(imageData).addOnSuccessListener {
                    Toast.makeText(this, "Imagem salva", Toast.LENGTH_SHORT).show()

                }
            }

        }

    }



    private fun uploadInfo(email: String, senha: String) {

        Toast.makeText(this, "FILHA DA PUTA", Toast.LENGTH_SHORT).show()

        val nome = binding.editNomeC.text.toString()
        val imgUrl = binding.btnCamera.toString()
        val uId = database.push().key!!

        //vai criar um usuario a partir do email e da senha
        //essa parte vai ser utilizada para o usuario fazer o login
        //atraves desses dois campos que seram salvos no authentication do firebase
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {

                    Toast.makeText(this, "Conta criada!", Toast.LENGTH_SHORT).show()

                    val intent= Intent(this,bemVindo::class.java)
                    finish()
                    startActivity(intent)
                }

                else{
                    Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
                }

            }
    }




   fun criarConta(uId:String,nome:String,email:String,imgUrl:String,senha:String){


       val usuarios = Usuario(nome,email,imgUrl,senha)

       database.child(uId).setValue(usuarios)
           .addOnCompleteListener {

               Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

           }.addOnFailureListener { err ->
               Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
           }

   }
}