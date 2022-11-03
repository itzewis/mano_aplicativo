package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class listPublicacao_Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_publicacao_perfil)


        var deletar = findViewById<ImageView>(R.id.deletarPubli)

        deletar.setOnClickListener {

            deletarPublicacao()

        }


    }

    //funcao que ira deletar um publicação se o uId for o mesmo do usuario que estiver logado
    private fun deletarPublicacao() {

        Toast.makeText(this, "deletei", Toast.LENGTH_SHORT).show()

    }
}