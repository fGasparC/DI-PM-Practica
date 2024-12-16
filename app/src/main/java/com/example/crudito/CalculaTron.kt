package com.example.crudito

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudito.databinding.ActivityCalculaTronBinding
import com.example.crudito.databinding.ActivityMemoryTronBinding

class CalculaTron : AppCompatActivity() {
    private lateinit var binding: ActivityCalculaTronBinding
    private lateinit var res: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        res=""
        binding = ActivityCalculaTronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cero.setOnClickListener{
            res+="0"
        }
        binding.uno.setOnClickListener{
            res+="1"
        }
        binding.dos.setOnClickListener{
            res+="2"
        }
        binding.tres.setOnClickListener{
            res+="3"
        }
        binding.cuatro.setOnClickListener{
            res+="4"
        }
        binding.cinco.setOnClickListener{
            res+="5"
        }
        binding.seis.setOnClickListener{
            res+="6"
        }
        binding.siete.setOnClickListener{
            res+="7"
        }
        binding.ocho.setOnClickListener{
            res+="8"
        }
        binding.nueve.setOnClickListener{
            res+="9"
        }
        binding.limpiar.setOnClickListener{
            res=""
        }
        binding.atras.setOnClickListener{
            res.last()
        }
        binding.ajustes.setOnClickListener{
            val intent=Intent(this,Ajustes::class.java)
            startActivity(intent)
        }
        binding.igual.setOnClickListener{

        }



    }
}