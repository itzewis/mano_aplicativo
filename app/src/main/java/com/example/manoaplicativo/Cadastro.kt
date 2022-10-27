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
import java.util.*

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage : FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var imgUrl : Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // a linha abaixo é usada para pegar a
        // instancia do banco de dados
        database = FirebaseDatabase.getInstance();

        // linha abaixo é  usada para pegar  a referencia do banco de dados firebase.
        dbRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        storage = FirebaseStorage.getInstance()


        binding.textLogin.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }

        //botao para o acesso a galeria para a escolha da foto de perfil
        binding.btnCamera.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }


        binding.btnCriarConta.setOnClickListener {
            val email = binding.editEmailC.text.toString()
            val senha = binding.editSenhaC.text.toString()
            val nome = binding.editNomeC.text.toString()

            if(senha.isEmpty() || email.isEmpty() || nome.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

            if(senha.length < 6){
                binding.editSenhaC.setError("Minímo 6 caracteres")
            }

            // se todas a condições acima forem verdadeiras
            // criara se o createUserWithEmailAndPassword
            // usando o objeto auth e passando a
            // senah e o email
            else{
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Conta Criada", Toast.LENGTH_SHORT).show()

                            adicionarRealTime(imgUrl = String())

                            salvarImagem()

                            finish()
                        } else {
                            Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
                        }
                 }

           }


    }


}

    private fun salvarImagem() {
        val referencia = storage.reference.child("imagem").child(Date().time.toString())
        referencia.putFile(imgUrl).addOnCompleteListener{
            if(it.isSuccessful){
                referencia.downloadUrl.addOnSuccessListener { task ->
                    adicionarRealTime(task.toString())
                }
            }

        }

    }


    //os campos presentes na tela cadastro serem adicionados no realtime database
    //logo apos a criacao do createUserWithEmailAndPassword
    private fun adicionarRealTime(imgUrl: String) {

        //para que cada Id seja diferente um do outro e que um dado nao substitua o outro
        val uId = dbRef.push().key

        val email = binding.editEmailC.text.toString()
        val senha = binding.editSenhaC.text.toString()
        val nome = binding.editNomeC.text.toString()


        val usuario = Usuario(email,uId,imgUrl,nome,senha)

        if(uId != null) {
            dbRef.child(uId).setValue(usuario)
                .addOnCompleteListener {
                    Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
                }

        }

    }


    //para que a imagem escolhida apareca la no btnCamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
                if(data.data != null){
                    imgUrl = data.data!!

                    binding.btnCamera.setImageURI(imgUrl)
                }
        }

    }

}