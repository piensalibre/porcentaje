package com.example.porcentajes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "MainActivity"
private const val PORCENTAJEINICIAL = 15

class MainActivity : AppCompatActivity() {

    private lateinit var etCantidadBase: EditText
    private lateinit var sbPorcentaje: SeekBar
    private lateinit var tvPorcentaje: TextView
    private lateinit var tvCantidadExtra: TextView
    private lateinit var tvCantidadTotal: TextView
    private lateinit var tvDescripcion: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etCantidadBase = findViewById(R.id.etCantidadBase)
        sbPorcentaje = findViewById(R.id.sbPorcentaje)
        tvPorcentaje = findViewById(R.id.tvPorcentaje)
        tvCantidadExtra = findViewById(R.id.tvCantidadExtra)
        tvCantidadTotal = findViewById(R.id.tvCantidadTotal)
        tvDescripcion = findViewById(R.id.tvDescripcion)

        sbPorcentaje.progress = PORCENTAJEINICIAL
        tvPorcentaje.text = "$PORCENTAJEINICIAL%"
        actualizarDescripcion(PORCENTAJEINICIAL)
        sbPorcentaje.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tvPorcentaje.text = "$p1%"
                calcularPorcentajeYTotal()
                actualizarDescripcion(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        etCantidadBase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                calcularPorcentajeYTotal()
            }

        })
    }

    private fun actualizarDescripcion(p1: Int) {
        val descripcion = when(p1){
            in 0..9 -> "Poco"
            in 10..14 -> "Aceptable"
            in 15..19 -> "Bueno"
            in 20..24 -> "Genial"
            else -> "Asombroso"
        }
        tvDescripcion.text = descripcion
    }

    private fun calcularPorcentajeYTotal() {
        if(etCantidadBase.text.isEmpty()){
            tvCantidadExtra.text = ""
            tvCantidadTotal.text = ""
            return
        }

        val cantidadBase = etCantidadBase.text.toString().toDouble()
        val porcentaje = sbPorcentaje.progress

        val extra = cantidadBase * porcentaje / 100
        val total = cantidadBase + extra
        tvCantidadExtra.text = "%.2f".format(extra)
        tvCantidadTotal.text = "%.2f".format(total)
    }
}