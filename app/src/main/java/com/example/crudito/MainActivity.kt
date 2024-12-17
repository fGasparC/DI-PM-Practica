package com.example.crudito

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

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
        val boton1=findViewById<ImageButton>(R.id.mainBoton1)
        val boton2=findViewById<ImageButton>(R.id.mainBoton2)
        boton1.setOnClickListener{
            val intent= Intent(this,MemoryTron::class.java)
            startActivity(intent)
        }
        boton2.setOnClickListener{
            val intent= Intent(this,CalculaTron::class.java)
            startActivity(intent)
        }
    }
}