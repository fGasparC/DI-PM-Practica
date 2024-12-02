package com.example.crudito

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudito.databinding.ActivityMemoryTronBinding



class MemoryTron : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTronBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryTronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos una array con 12 cartas de 6 tipos.

        //La barajamos ->

    }
}