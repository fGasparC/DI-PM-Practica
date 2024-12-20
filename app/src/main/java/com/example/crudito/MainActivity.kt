package com.example.crudito

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private lateinit var vibrator: Vibrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val boton1=findViewById<MaterialCardView>(R.id.mainBoton1)
        val boton2=findViewById<MaterialCardView>(R.id.mainBoton2)
        boton1.setOnClickListener{
            val intent= Intent(this,MemoryTron::class.java)
            vibrator.vibrate(50)
            startActivity(intent)
        }
        boton2.setOnClickListener{
            val intent= Intent(this,CalculaTron::class.java)
            vibrator.vibrate(50)
            startActivity(intent)
        }
    }
}