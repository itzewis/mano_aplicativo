package com.example.manoaplicativo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.manoaplicativo.databinding.ActivityBemVindoBinding

class bemVindo : AppCompatActivity() {

    private lateinit var binding : ActivityBemVindoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bem_vindo)

        binding.btnContinuarTelaInicial.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }

    }
}