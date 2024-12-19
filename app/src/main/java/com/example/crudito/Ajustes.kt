package com.example.crudito

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudito.databinding.ActivityAjustesBinding
import com.example.crudito.databinding.ActivityMemoryTronBinding

class Ajustes : AppCompatActivity() {
    private lateinit var binding: ActivityAjustesBinding
    private lateinit var sP: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sP=getSharedPreferences("Ajustes",MODE_PRIVATE)
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var min=sP.getInt("minimo",0)
        var max=sP.getInt("maximo",10)
        var operaciones= sP.getString("Operaciones","+ -").toString()
        var timer=sP.getInt("timer",20)

        binding.guardarOpciones.setOnClickListener{
            if(binding.valorMinET.text?.isNotEmpty()!!){
                min=binding.valorMinET.text.toString().toInt()
                if(min<0){
                    min=0
                }
                sP.edit().putInt("minimo", min).apply()
            }
            if(binding.valorMaxET.text?.isNotEmpty()!!){
                max=binding.valorMaxET.text.toString().toInt()
                if(min>max){
                    max=min+5
                }
                sP.edit().putInt("maximo", max).apply()
            }
            if(binding.valorCuentaAtrasET.text?.isNotEmpty()!!){
                timer=binding.valorCuentaAtrasET.text.toString().toInt()
                if(timer<0){
                    timer=20
                }
                sP.edit().putInt("timer",timer).apply()
            }
            if(binding.cbsuma.isChecked && binding.cbresta.isChecked && binding.cbmultiplicacion.isChecked){
                sP.edit().putString("Operaciones","+ - *").apply()
            }
            else if(binding.cbsuma.isChecked && binding.cbresta.isChecked){
                sP.edit().putString("Operaciones","+ -").apply()
            }
            else if(binding.cbsuma.isChecked && binding.cbmultiplicacion.isChecked){
                sP.edit().putString("Operaciones","+ *").apply()
            }
            else if(binding.cbresta.isChecked && binding.cbmultiplicacion.isChecked){
                sP.edit().putString("Operaciones","- *").apply()
            }
            else if(binding.cbmultiplicacion.isChecked){
                sP.edit().putString("Operaciones","*").apply()
            }
            else if(binding.cbresta.isChecked){
                sP.edit().putString("Operaciones","-").apply()
            }
            else if(binding.cbsuma.isChecked){
                sP.edit().putString("Operaciones","+").apply()
            }
            val intent= Intent(this,CalculaTron::class.java)
            this.finish()
            startActivity(intent)
        }
    }
}