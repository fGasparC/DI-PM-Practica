package com.example.crudito

import android.media.Image
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudito.databinding.ActivityMemoryTronBinding



class MemoryTron : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryTronBinding
    var contador=0
    var cartasEncontradas=mutableListOf<ImageButton?>()
    var cartaSeleccionadas= arrayOfNulls<ImageButton>(2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryTronBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos una array con 12 cartas de 6 tipos.
        var contador = 0
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
            binding.cartita12
        )
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
            R.drawable.hkespadas
        )
        barajaReversa.shuffle()
        for(i in 0 until baraja.size){
            voltearCarta(baraja,barajaReversa,i)
        }



    }
    fun voltearCarta(a: MutableList<ImageButton>,b: MutableList<Int>,i: Int){
            a[i].setOnClickListener {
                a[i].setImageResource(b[i])
                contador++
                a[i].isClickable=false
                Log.d("Contador if", contador.toString())
                cartaSeleccionadas[contador-1]=a[i]
                Log.d("Carta selecionada", cartaSeleccionadas[contador-1].toString())
                Log.d("Carta selecionada drawable",cartaSeleccionadas[contador-1]?.imageAlpha.toString())
                if(contador==2){
                    if(cartaSeleccionadas[0]==cartaSeleccionadas[1]){
                        cartasEncontradas.add(cartaSeleccionadas[0])
                        Log.d("Carta encontrada", cartasEncontradas[0].toString())
                        cartasEncontradas.add(cartaSeleccionadas[1])
                        Log.d("Carta encontrada", cartasEncontradas[1].toString())
                    }
                    for(i in 0 until a.size){
                        a[i].isClickable=false
                    }

                    desvoltearCarta(a)
                }
            }
    }
    fun desvoltearCarta(a:MutableList<ImageButton>){
        object: CountDownTimer(1000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                for (i in 0 until a.size){
                    if(!cartasEncontradas.contains(a[i])) {
                        a[i].isClickable=true
                        a[i].setImageResource(R.drawable.reversocartaa)
                    }
                }
                contador=0
            }
        }.start()

    }
}