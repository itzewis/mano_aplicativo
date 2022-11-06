package com.example.manoaplicativo



import android.location.LocationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.manoaplicativo.adapter.Usuario
import com.example.manoaplicativo.databinding.ActivityCadastroBinding
import com.google.android.gms.common.api.GoogleApi.Settings
import com.google.api.Context
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
    private var LOCALIZACAO_CODIGO = 2020



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

            pedirPermissao(android.Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE)

        }


        binding.btnCriarConta.setOnClickListener {
            val email = binding.editEmailC.text.toString()
            val senha = binding.editSenhaC.text.toString()
            val nome = binding.editNomeC.text.toString()

            pegarLocalizacao()

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


                        } else {
                            Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
                        }
                 }

           }


    }


}

    private fun pegarLocalizacao() : Boolean{

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCALIZACAO_CODIGO)

        }
        else{

            salvarImagem()

        }

        return false
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

        if (requestCode == LOCALIZACAO_CODIGO){

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Acesso permitido", Toast.LENGTH_SHORT).show()

            }

            else{
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
        val email = binding.editEmailC.text.toString()
        val senha = binding.editSenhaC.text.toString()
        val nome = binding.editNomeC.text.toString()



        val usuario = Usuario(email,uId,imgUrl,nome,senha)

        if(uId != null) {
            dbRef.child(uId).setValue(usuario)
                .addOnCompleteListener {

                    Toast.makeText(this, "Conta Criada", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this,login::class.java)
                    startActivity(intent)
                    finish()
                }

        }

    }


    //verifica se o GPS esta ativado
   private fun gpsAtivado(): Boolean {
        var locationManager: LocationManager = getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    //para que a imagem escolhida apareca la no campo btnCamera
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