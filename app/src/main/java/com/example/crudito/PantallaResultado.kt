package com.example.crudito

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class PantallaResultado : AppCompatActivity() {
    private lateinit var sP: SharedPreferences
    private lateinit var vibrator: Vibrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        sP=getSharedPreferences("Ajustes",MODE_PRIVATE)
        val aciertosT=sP.getInt("aciertosTotales",0)
        val aciertos=sP.getInt("aciertos",0)
        val fallosT=sP.getInt("fallosTotales",0)
        val fallos=sP.getInt("fallos",0)
        val acP=findViewById<MaterialTextView>(R.id.contadorAciertosPartida)
        val acG=findViewById<MaterialTextView>(R.id.contadorAciertosGlobales)
        val faP=findViewById<MaterialTextView>(R.id.contadorFallosPartida)
        val faG=findViewById<MaterialTextView>(R.id.contadorFallosGlobales)

            acP.text=aciertos.toString()
            acG.text=aciertosT.toString()
            faP.text=fallos.toString()
            faG.text=fallosT.toString()
        val botonMenu=findViewById<MaterialButton>(R.id.btnMenu)
        val botonVolverJugar=findViewById<MaterialButton>(R.id.btnVolverJugar)
        botonMenu.setOnClickListener{
            vibrator.vibrate(50)
            val intent= Intent(this, MainActivity::class.java)
            this.finish()
            startActivity(intent)
        }
        botonVolverJugar.setOnClickListener{
            vibrator.vibrate(50)
            val intent= Intent(this, CalculaTron::class.java)
            this.finish()
            startActivity(intent)
        }
    }
}