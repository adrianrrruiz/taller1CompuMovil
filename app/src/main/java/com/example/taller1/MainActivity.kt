package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import com.example.taller1.model.Destino

class MainActivity : AppCompatActivity() {
    companion object {
        val destinosFavoritos = mutableListOf<Destino>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        var filtroSeleccionado = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Obtener el valor seleccionado
                filtroSeleccionado = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción opcional en caso de que no se seleccione nada
            }
        }

        findViewById<Button>(R.id.button1).setOnClickListener { startActivity(Intent(this, ListaDestinosActivity::class.java).putExtra("filtroSeleccionado", filtroSeleccionado)) }

        findViewById<Button>(R.id.button2).setOnClickListener { startActivity(Intent (this, DestinosFavoritosActivity::class.java)) }

        findViewById<Button>(R.id.button3).setOnClickListener{ startActivity(Intent (this, DestinoRecomendadoActivity::class.java)) }
    }
}
