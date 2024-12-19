package com.example.crudito

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudito.databinding.ActivityCalculaTronBinding
import com.example.crudito.databinding.ActivityMemoryTronBinding
import kotlin.random.Random

class CalculaTron : AppCompatActivity() {
    private lateinit var binding: ActivityCalculaTronBinding
    private lateinit var res: String
    private lateinit var operaciones: String
    private lateinit var sP: SharedPreferences
    var min=0
    var max=10000
    var aciertos=0
    var fallos=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent=Intent(this,PantallaResultado::class.java)

        sP=getSharedPreferences("Ajustes",MODE_PRIVATE)
        var aciertosTotales=sP.getInt("aciertosTotales",0)
        var fallosTotales=sP.getInt("fallosTotales",0)
        min=sP.getInt("minimo",0)
        max=sP.getInt("maximo",10)
        operaciones= sP.getString("Operaciones","+ -").toString()
        var timer=sP.getInt("timer",20)

        var contador=object : CountDownTimer((timer*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer-=1
                binding.timer.text=timer.toString()
            }
            override fun onFinish() {
                sP.edit().putInt("fallos",fallos).apply()
                sP.edit().putInt("aciertos",aciertos).apply()
                sP.edit().putInt("aciertosTotales",(aciertos+aciertosTotales)).apply()
                sP.edit().putInt("fallosTotales",(fallos+fallosTotales)).apply()
                this@CalculaTron.finish()
                startActivity(intent)
            }
        }.start()
        res=""
        binding = ActivityCalculaTronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.operacionActual.text=generarOperacion()
        binding.operacionSiguiente.text=generarOperacion()

        binding.cero.setOnClickListener{
            res+="0"
            escribirRes()
        }
        binding.uno.setOnClickListener{
            res+="1"
            escribirRes()
        }
        binding.dos.setOnClickListener{
            res+="2"
            escribirRes()
        }
        binding.tres.setOnClickListener{
            res+="3"
            escribirRes()
        }
        binding.cuatro.setOnClickListener{
            res+="4"
            escribirRes()
        }
        binding.cinco.setOnClickListener{
            res+="5"
            escribirRes()
        }
        binding.seis.setOnClickListener{
            res+="6"
            escribirRes()
        }
        binding.siete.setOnClickListener{
            res+="7"
            escribirRes()
        }
        binding.ocho.setOnClickListener{
            res+="8"
            escribirRes()
        }
        binding.nueve.setOnClickListener{
            res+="9"
            escribirRes()
            Log.d("String",res)
        }
        binding.limpiar.setOnClickListener{
            res=""
            escribirRes()
        }
        binding.atras.setOnClickListener{
            res=res.dropLast(1)
            escribirRes()
        }
        binding.minus.setOnClickListener{
            res="-"
            escribirRes()
        }
        binding.ajustes.setOnClickListener{
            val intent=Intent(this,Ajustes::class.java)
            contador.cancel()
            this.finish()
            startActivity(intent)
        }
        binding.igual.setOnClickListener{
            if(res=="" || res=="-"){
                Toast.makeText(this, "Resuelve la operación", Toast.LENGTH_SHORT).show()
                res=""
                escribirRes()
            }
            else if(res=="-0"){
                Toast.makeText(this, "En aritmetica ordinaria no existe 0 negativo.", Toast.LENGTH_SHORT).show()
                res=""
                escribirRes()
            }
            else{
                Log.d("Estoy pasando por aqui","hola")
                binding.operacionPasada.text="${binding.operacionActual.text}${binding.resultado.text}"
                binding.operacionActual.text="${binding.operacionSiguiente.text}"
                binding.operacionSiguiente.text=generarOperacion()
                res=""
                escribirRes()
                if(!evaluarOperacion(binding.operacionPasada.text.toString())) {
                    binding.evaluacionOperacionImagen.setImageResource(R.drawable.hkincorrecto)
                    fallos++
                    binding.contadorFallos.text=fallos.toString()
                }
                else{
                    binding.evaluacionOperacionImagen.setImageResource(R.drawable.hkcorrecto)
                    aciertos++
                    binding.contadorAciertos.text=aciertos.toString()
                }
            }

        }
    }
    fun escribirRes(){
        binding.resultado.text=res
    }
    fun generaNumeroRandom():Int{
        return Random.nextInt(min,max)
    }
    fun generarSimboloRandom():String{
        return operaciones.split(" ").random()
    }
    fun generarOperacion(): String{
        return ("${generaNumeroRandom()} ${generarSimboloRandom()} ${generaNumeroRandom()} = ")
    }
    fun evaluarOperacion(operacion:String): Boolean{
        val partes= operacion.split(" ")
        if(partes[1]=="+"){
            if(partes[0].toInt()+partes[2].toInt()==partes[4].toInt()){
                return true
            }
            else return false
        }
        else if(partes[1]=="-"){
            if(partes[0].toInt()-partes[2].toInt()==partes[4].toInt()){
                return true
            }
            else return false
        }
        else if(partes[1]=="*"){
            if(partes[0].toInt()*partes[2].toInt()==partes[4].toInt()){
                return true
            }
            else return false
        }
        else return false
    }
}