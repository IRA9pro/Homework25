package com.example.notes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.notes.R
import com.example.notes.data.local.Pref

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pref = Pref(this)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navGraph = navHost.navController.navInflater.inflate(R.navigation.nav_host)

        navGraph.setStartDestination(
            if (pref.isIntroShown())
                R.id.mainFragment
            else R.id.introductionFragment
        )

        navHost.navController.graph = navGraph
    }
}