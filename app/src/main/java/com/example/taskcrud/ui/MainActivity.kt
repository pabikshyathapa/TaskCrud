package com.example.taskcrud.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskcrud.R
import com.example.taskcrud.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.registerbutton.setOnClickListener {
            var intent = Intent(this@MainActivity, RegistrationActivity::class.java
            )
            startActivity(intent)
        }
        mainBinding.loginbutton.setOnClickListener {
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}