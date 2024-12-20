package com.example.crudito

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.crudito.databinding.ActivityCalculaTronBinding
import kotlin.random.Random

class CalculaTron : AppCompatActivity() {
    private lateinit var binding: ActivityCalculaTronBinding
    private lateinit var res: String
    private lateinit var operaciones: String
    private lateinit var sP: SharedPreferences
    private lateinit var coin: SoundPool
    private lateinit var error: SoundPool
    private lateinit var vibrator: Vibrator
    private lateinit var contador: CountDownTimer
    private var coinId= 0
    private var errorId=0

    var min=0
    var max=10000
    var aciertos=0
    var fallos=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalculaTronBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent=Intent(this,PantallaResultado::class.java)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        coin= SoundPool.Builder().setMaxStreams(1).build()
        coinId = coin.load(this, R.raw.coin, 1)

        error= SoundPool.Builder().setMaxStreams(1).build()
        errorId= error.load(this, R.raw.error, 1)

        sP=getSharedPreferences("Ajustes",MODE_PRIVATE)
        var aciertosTotales=sP.getInt("aciertosTotales",0)
        var fallosTotales=sP.getInt("fallosTotales",0)
        min=sP.getInt("minimo",0)
        max=sP.getInt("maximo",10)
        operaciones= sP.getString("Operaciones","+ -").toString()
        var timer=sP.getInt("timer",20)

         contador=object : CountDownTimer((timer*1000).toLong(), 1000) {
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


        binding.operacionActual.text=generarOperacion()
        binding.operacionSiguiente.text=generarOperacion()

        binding.cero.setOnClickListener{
            vibrator.vibrate(50)
            res+="0"
            escribirRes()
        }
        binding.uno.setOnClickListener{
            vibrator.vibrate(50)
            res+="1"
            escribirRes()
        }
        binding.dos.setOnClickListener{
            vibrator.vibrate(50)
            res+="2"
            escribirRes()
        }
        binding.tres.setOnClickListener{
            vibrator.vibrate(50)
            res+="3"
            escribirRes()
        }
        binding.cuatro.setOnClickListener{
            vibrator.vibrate(50)
            res+="4"
            escribirRes()
        }
        binding.cinco.setOnClickListener{
            vibrator.vibrate(50)
            res+="5"
            escribirRes()
        }
        binding.seis.setOnClickListener{
            vibrator.vibrate(50)
            res+="6"
            escribirRes()
        }
        binding.siete.setOnClickListener{
            vibrator.vibrate(50)
            res+="7"
            escribirRes()
        }
        binding.ocho.setOnClickListener{
            vibrator.vibrate(50)
            res+="8"
            escribirRes()
        }
        binding.nueve.setOnClickListener{
            vibrator.vibrate(50)
            res+="9"
            escribirRes()
        }
        binding.limpiar.setOnClickListener{
            vibrator.vibrate(50)
            res=""
            escribirRes()
        }
        binding.atras.setOnClickListener{
            vibrator.vibrate(50)
            res=res.dropLast(1)
            escribirRes()
        }
        binding.minus.setOnClickListener{
            vibrator.vibrate(50)
            res="-"
            escribirRes()
        }
        binding.ajustes.setOnClickListener{
            vibrator.vibrate(50)
            val intent=Intent(this,Ajustes::class.java)
            contador.cancel()
            this.finish()
            startActivity(intent)
        }
        binding.igual.setOnClickListener{
            vibrator.vibrate(200)
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
                val fadeAnimation = ObjectAnimator.ofFloat(binding.operacionSiguiente, "alpha", 0f, 1f)
                fadeAnimation.duration = 500 // Duración de la animación en milisegundos
                fadeAnimation.start()
                val fadeAnimation2 = ObjectAnimator.ofFloat(binding.operacionPasada, "alpha", 0f, 1f)
                fadeAnimation2.duration = 500 // Duración de la animación en milisegundos
                fadeAnimation2.start()
                binding.operacionPasada.text="${binding.operacionActual.text}${binding.resultado.text}"
                binding.operacionActual.text="${binding.operacionSiguiente.text}"
                binding.operacionSiguiente.text=generarOperacion()
                res=""
                escribirRes()
                if(!evaluarOperacion(binding.operacionPasada.text.toString())) {
                    error.play(errorId, 1f, 1f, 1, 0, 1f)
                    Glide.with(this)
                        .load(R.drawable.hksad)
                        .into(binding.evaluacionOperacionImagen)
                    fallos++
                    binding.contadorFallos.text=fallos.toString()
                }
                else{
                    coin.play(coinId, 1f, 1f, 1, 0, 1f)
                    Glide.with(this)
                        .load(R.drawable.hkhappy2)
                        .into(binding.evaluacionOperacionImagen)
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
    override fun onBackPressed() {
        contador.cancel()
        super.onBackPressed()
    }
}