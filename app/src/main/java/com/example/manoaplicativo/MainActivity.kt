package com.example.manoaplicativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.manoaplicativo.fragmentos.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val homeFragment = Home()
        val chatFragment = Chat()
        val criarPubliFragment = Criar_Publicacao()
        val pedidosFragment = Pedidos()
        val perfilFragment = Perfil()

        makeCurrentFragment(homeFragment)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.btnHome -> makeCurrentFragment(homeFragment)
                R.id.btnChat ->makeCurrentFragment(chatFragment)
                R.id.btnCriar ->makeCurrentFragment(criarPubliFragment)
                R.id.btnPedidos ->makeCurrentFragment(pedidosFragment)
                R.id.btnPerfil ->makeCurrentFragment(perfilFragment)

            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameInicio,fragment)
            commit()
        }
}