package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.taller1.model.Destino

class DestinoRecomendadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino_recomendado)

        val destinosFavoritos = MainActivity.destinosFavoritos
        val categoriaMasFrecuente = obtenerCategoriaMasFrecuente(destinosFavoritos)
        val destinoRecomendado = if (categoriaMasFrecuente != null) {
            obtenerDestinoAleatorioPorCategoria(destinosFavoritos, categoriaMasFrecuente)
        } else {
            Destino(0, "NA", "NA", "NA", "NA", 0) // Si no hay favoritos, mostrar "NA"
        }

        findViewById<TextView>(R.id.nombre).text = destinoRecomendado.nombre
        findViewById<TextView>(R.id.pais).text = destinoRecomendado.pais
        findViewById<TextView>(R.id.categoria).text = destinoRecomendado.categoria
        findViewById<TextView>(R.id.plan).text = destinoRecomendado.plan
        findViewById<TextView>(R.id.precio).text = "USD " + destinoRecomendado.precio.toString()

    }

    private fun obtenerCategoriaMasFrecuente(destinosFavoritos: List<Destino>): String? {
        if (destinosFavoritos.isNullOrEmpty()) return null

        val categorias = destinosFavoritos.map { it.categoria }
        val categoriasFrecuentes = categorias.groupingBy { it }.eachCount()
        return categoriasFrecuentes.maxByOrNull { it.value }?.key
    }

    private fun obtenerDestinoAleatorioPorCategoria(destinosFavoritos: List<Destino>, categoria: String): Destino {
        val destinosFiltrados = destinosFavoritos.filter { it.categoria == categoria }
        return destinosFiltrados.random()
    }

}