package com.example.crudito

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.crudito.databinding.ActivityMemoryTronBinding
import com.google.android.material.imageview.ShapeableImageView



class MemoryTron : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTronBinding
    private lateinit var vidasImg: MutableList<ShapeableImageView>
    var contador = 0
    var vidas = 5
    var aciertos=0
    var cartasEncontradas = mutableListOf<ImageButton?>()
    var imagenesEncontradas = arrayOfNulls<Int>(2)
    var cartaSeleccionadas = arrayOfNulls<ImageButton>(2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryTronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos una array con 12 cartas de 6 tipos.
        //var contador = 0
        vidasImg =mutableListOf(
            binding.vida1,
            binding.vida2,
            binding.vida3,
            binding.vida4,
            binding.vida5)
        var baraja = mutableListOf(
            binding.cartita1,
            binding.cartita2,
            binding.cartita3,
            binding.cartita4,
            binding.cartita5,
            binding.cartita6,
            binding.cartita7,
            binding.cartita8,
            binding.cartita9,
            binding.cartita10,
            binding.cartita11,
            binding.cartita12)
        var barajaReversa = mutableListOf(
            R.drawable.hkbazooka,
            R.drawable.hkbazooka,
            R.drawable.hkbastos,
            R.drawable.hkbastos,
            R.drawable.hkcopas,
            R.drawable.hkcopas,
            R.drawable.hkpistolera,
            R.drawable.hkpistolera,
            R.drawable.hklanzallamas,
            R.drawable.hklanzallamas,
            R.drawable.hkespadas,
            R.drawable.hkespadas)
        barajaReversa.shuffle()
        for (i in 0 until baraja.size) {
            voltearCarta(baraja, barajaReversa, i)
        }
        binding.btnJugardenuevo.setOnClickListener{
            for(i in vidasImg){
                i.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .rotation(0f)
                    .setDuration(1000)
                i.setImageResource(R.drawable.corazoncito)

            }
            for(i in baraja){
                i.isClickable = true
                i.setImageResource(R.drawable.reversocartaa)
                val fadeAnimation = ObjectAnimator.ofFloat(i, "alpha", 0f, 1f)
                fadeAnimation.duration = 1000 // Duración de la animación en milisegundos
                fadeAnimation.startDelay = 500 // Retraso antes de que comience la animación
                fadeAnimation.start()
            }
            binding.victoriaimg.visibility=View.GONE
            binding.derrotaimg.visibility=View.GONE
            binding.btnJugardenuevo.visibility=View.GONE
            binding.btnSalir.visibility=View.GONE
            contador = 0
            vidas = 5
            aciertos=0
            cartasEncontradas = mutableListOf<ImageButton?>()
            barajaReversa.shuffle()
            for (i in 0 until baraja.size) {
                voltearCarta(baraja, barajaReversa, i)
            }
        }
        binding.btnSalir.setOnClickListener{
            finish()
        }


    }

    fun voltearCarta(a: MutableList<ImageButton>, b: MutableList<Int>, i: Int) {
        a[i].setOnClickListener {
            val rotationAnimator = ObjectAnimator.ofFloat(a[i], "rotationY", 90f, 180f)
            rotationAnimator.duration = 300 // Duración en milisegundos
            rotationAnimator.start()
            a[i].setImageResource(b[i])
            contador++
            a[i].isClickable = false
            Log.d("Contador if", contador.toString())
            cartaSeleccionadas[contador - 1] = a[i]
            imagenesEncontradas[contador - 1] = b[i]
            Log.d("Imagen seleccionada", imagenesEncontradas[contador - 1].toString())
            Log.d("Carta selecionada", cartaSeleccionadas[contador - 1].toString())
            if (contador == 2) {
                if (imagenesEncontradas[0] == imagenesEncontradas[1]) {
                    cartasEncontradas.add(cartaSeleccionadas[0])
                    Log.d("Carta encontrada", cartasEncontradas[0].toString())
                    cartasEncontradas.add(cartaSeleccionadas[1])
                    Log.d("Carta encontrada", cartasEncontradas[1].toString())
                    aciertos++
                    if(aciertos==6) victoria(a)
                } else {
                    vidas--
                    vidasImg[vidas].animate()
                        .scaleX(0f)
                        .scaleY(0f)
                        .rotation(720f)
                        .setDuration(1000)
                        .withEndAction {
                            vidasImg[vidas].setImageDrawable(null)
                        }
                        .start()
                    Log.d("vidas", vidas.toString())
                    if(vidas==0) derrota(a)
                }
                for (i in 0 until a.size) {
                    a[i].isClickable = false
                }

                desvoltearCarta(a)
            }
        }
    }
    fun desvoltearCarta(a: MutableList<ImageButton>) {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                for (i in 0 until a.size) {
                    if (!cartasEncontradas.contains(a[i])) {
                        a[i].isClickable = true
                        a[i].setImageResource(R.drawable.reversocartaa)
                    }
                }
                contador = 0
            }
        }.start()
    }
    fun derrota(a: MutableList<ImageButton>) {
        for(i in a){
            i.isClickable=false
            val fadeAnimation = ObjectAnimator.ofFloat(i, "alpha", 1f, 0f)
            fadeAnimation.duration = 1000 // Duración de la animación en milisegundos
            fadeAnimation.startDelay = 500 // Retraso antes de que comience la animación
            fadeAnimation.start()
        }
        binding.derrotaimg.visibility=View.VISIBLE
        binding.btnJugardenuevo.visibility=View.VISIBLE
        binding.btnSalir.visibility=View.VISIBLE
        //binding.derrotaimg.visibility= View.GONE
        Glide.with(this)
            .load(R.drawable.hksad)
            .into(binding.derrotaimg)

    }
    fun victoria(a: MutableList<ImageButton>){
        for(i in a){
            i.isClickable=false
            val fadeAnimation = ObjectAnimator.ofFloat(i, "alpha", 1f, 0f)
            fadeAnimation.duration = 1000 // Duración de la animación en milisegundos
            fadeAnimation.startDelay = 500 // Retraso antes de que comience la animación
            fadeAnimation.start()
        }
        binding.victoriaimg.visibility=View.VISIBLE
        binding.btnJugardenuevo.visibility=View.VISIBLE
        binding.btnSalir.visibility=View.VISIBLE
        Glide.with(this)
            .load(R.drawable.hkhappy)
            .into(binding.victoriaimg)
    }
}