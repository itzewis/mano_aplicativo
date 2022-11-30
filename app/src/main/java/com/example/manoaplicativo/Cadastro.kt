package com.example.manoaplicativo



import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.databinding.ActivityCadastroBinding
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage : FirebaseStorage
    private lateinit var database : FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var imgUrl : Uri
    private var STORAGE_PERMISSION_CODE = 113


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        // a linha abaixo é usada para pegar a
        // instancia do banco de dados
        database = FirebaseDatabase.getInstance();

        // linha abaixo é  usada para pegar  a referencia do banco de dados firebase.
        dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")

        storage = FirebaseStorage.getInstance()


        binding.textLogin.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }

        //botao para o acesso a galeria para a escolha da foto de perfil
        binding.btnCamera.setOnClickListener {

            pedirPermissao(android.Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE)

        }


        binding.btnCriarConta.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenhaC.text.toString()
            val nome = binding.editNomeC.text.toString()
            val telefone = binding.editTelefone.text.toString()



            if(senha.isEmpty() || email.isEmpty() || nome.isEmpty() || telefone.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

            if(senha.length < 6) {
                binding.editSenhaC.error = "Minímo 6 caracteres"
            }

             if(telefone.length <= 11){

                 binding.editTelefone.error = "Minimo 11 caracteres"

             }
             else{

                 auth.createUserWithEmailAndPassword(email, senha)
                     .addOnCompleteListener(this) {
                         if(it.isSuccessful) {


                             salvarImagem()


                         } else {
                             Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
                         }
                     }

             }

            // se todas a condições acima forem verdadeiras
            // criara se o createUserWithEmailAndPassword
            // usando o objeto auth e passando a
            // senah e o email


    }



}



    //pedi permissao do usuario para ter acesso a galeria
    private fun pedirPermissao(permission: String, requestCode: Int){

        if(ContextCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)

        }

        else{
            //caso o acesso seja permitido vai da acesso a galeria
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }

    }

    //verificação do pedirpermissao

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == STORAGE_PERMISSION_CODE){
            //caso a permissao seja garantida
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Acesso permitido", Toast.LENGTH_SHORT).show()

                //com o acesso permitido ira dar acesso a galeria para a escolha de uma imagem
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(intent,1)

            }else{
                //caso seja negado
                Toast.makeText(this, "Acesso negado", Toast.LENGTH_SHORT).show()
            }
        }



    }

    //salva a imagem no storage firebase
    private fun salvarImagem() {

        val uId = FirebaseAuth.getInstance().currentUser!!.uid

        val referencia = storage.reference.child("imagens").child(uId)
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

        //para que cada Id do usuario seja diferente uma da outra e que um dado nao seja substitua pelo o outro
        val uId = FirebaseAuth.getInstance().currentUser!!.uid

        //transformação vdas variaves de texto para string
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenhaC.text.toString()
        val nome = binding.editNomeC.text.toString()
        val numero = binding.editTelefone.text.toString()

        val usuario = Usuario(email,uId, imgUrl, nome, senha,numero)

        dbRef.child(uId).setValue(usuario)
            .addOnCompleteListener {

                Toast.makeText(this, "Conta Criada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,login::class.java)
                startActivity(intent)
                finish()
            }

    }



    //para que a imagem escolhida apareca la no campo btnCamera
    @Deprecated("Deprecated in Java")
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