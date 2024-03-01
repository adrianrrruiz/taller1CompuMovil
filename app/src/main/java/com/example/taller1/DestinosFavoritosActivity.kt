package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.taller1.model.Destino

class DestinosFavoritosActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String> // Adaptador para mostrar solo los nombres de los destinos
    private var destinosFavoritos: MutableList<Destino> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinos_favoritos)

        listView = findViewById(R.id.destinosFav)

        // Obtener la lista de destinos favoritos del companion object en MainActivity
        destinosFavoritos = MainActivity.destinosFavoritos
        val nombresDestinos = destinosFavoritos.map { it.nombre }

        // Actualizar la interfaz de usuario con la lista de nombres de destinos favoritos
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresDestinos)
        listView.adapter = adapter
    }
}