package com.example.crudito

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.crudito.databinding.ActivityMemoryTronBinding
import com.google.android.material.imageview.ShapeableImageView



class MemoryTron : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTronBinding
    private lateinit var vidasImg: MutableList<ShapeableImageView>
    private lateinit var vibrator: Vibrator

    private lateinit var coin: SoundPool
    private lateinit var derrota: SoundPool
    private lateinit var error: SoundPool
    private lateinit var flipcard: SoundPool
    private lateinit var win: SoundPool

    private var coinId= 0
    private var derrotaId=0
    private var errorId=0
    private var flipcardId=0
    private var winId=0

    var contador = 0
    var vidas = 5
    var aciertos=0
    var cartasEncontradas = mutableListOf<ImageButton?>()
    var imagenesEncontradas = arrayOfNulls<Int>(2)
    var cartaSeleccionadas = arrayOfNulls<ImageButton>(2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMemoryTronBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        coin=SoundPool.Builder().setMaxStreams(1).build()
        coinId = coin.load(this, R.raw.coin, 1)

        derrota=SoundPool.Builder().setMaxStreams(1).build()
        derrotaId=derrota.load(this, R.raw.derrota, 1)

        error=SoundPool.Builder().setMaxStreams(1).build()
        errorId= error.load(this, R.raw.error, 1)

        flipcard=SoundPool.Builder().setMaxStreams(1).build()
        flipcardId=flipcard.load(this, R.raw.flipcard, 1)

        win=SoundPool.Builder().setMaxStreams(1).build()
        winId= win.load(this, R.raw.win, 1)



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
            vibrator.vibrate(50)
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
            binding.cartelResultado.visibility=View.GONE
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
            vibrator.vibrate(50)
            vibrator.vibrate(50)
            finish()
        }


    }

    fun voltearCarta(a: MutableList<ImageButton>, b: MutableList<Int>, i: Int) {
        a[i].setOnClickListener {
            vibrator.vibrate(100)
            val rotationAnimator = ObjectAnimator.ofFloat(a[i], "rotationY", 270f, 360f)
            flipcard.play(flipcardId, 1f, 1f, 0, 0, 1f)
            rotationAnimator.duration = 300 // Duración en milisegundos
            rotationAnimator.start()
            a[i].setImageResource(b[i])
            contador++
            a[i].isClickable = false
            cartaSeleccionadas[contador - 1] = a[i]
            imagenesEncontradas[contador - 1] = b[i]
            if (contador == 2) {
                if (imagenesEncontradas[0] == imagenesEncontradas[1]) {
                    coin.play(coinId, 1f, 1f, 1, 0, 1f)
                    cartasEncontradas.add(cartaSeleccionadas[0])
                    cartasEncontradas.add(cartaSeleccionadas[1])
                    aciertos++
                    if(aciertos==6) victoria(a)
                } else {
                    error.play(errorId, 1f, 1f, 1, 0, 1f)
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
        derrota.play(derrotaId, 1f, 1f, 2, 0, 1f)
        vibrator.vibrate(1000)
        val mP=MediaPlayer.create(this,R.raw.derrota)
        mP.start()
        mP.setOnCompletionListener{
            mP.release()
        }
        for(i in a){
            i.isClickable=false
            val fadeAnimation = ObjectAnimator.ofFloat(i, "alpha", 1f, 0f)
            fadeAnimation.duration = 1000 // Duración de la animación en milisegundos
            fadeAnimation.startDelay = 500 // Retraso antes de que comience la animación
            fadeAnimation.start()
        }
        binding.cartelResultado.text="DERROTA"
        binding.cartelResultado.setTextColor(Color.RED)
        binding.cartelResultado.visibility=View.VISIBLE
        binding.derrotaimg.visibility=View.VISIBLE
        binding.btnJugardenuevo.visibility=View.VISIBLE
        binding.btnSalir.visibility=View.VISIBLE
        Glide.with(this)
            .load(R.drawable.hksad)
            .into(binding.derrotaimg)

    }
    fun victoria(a: MutableList<ImageButton>){
        win.play(winId, 1f, 1f, 2, 0, 1f)
        vibrator.vibrate(1000)
        for(i in a){
            i.isClickable=false
            val fadeAnimation = ObjectAnimator.ofFloat(i, "alpha", 1f, 0f)
            fadeAnimation.duration = 1000 // Duración de la animación en milisegundos
            fadeAnimation.startDelay = 500 // Retraso antes de que comience la animación
            fadeAnimation.start()
        }

        binding.cartelResultado.text="VICTORIA"
        binding.cartelResultado.setTextColor(Color.GREEN)
        binding.cartelResultado.visibility=View.VISIBLE
        binding.victoriaimg.visibility=View.VISIBLE
        binding.btnJugardenuevo.visibility=View.VISIBLE
        binding.btnSalir.visibility=View.VISIBLE
        Glide.with(this)
            .load(R.drawable.hkhappy)
            .into(binding.victoriaimg)
    }
}