package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.taller1.model.Destino
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ListaDestinosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val filtroSeleccionado = intent.getStringExtra("filtroSeleccionado")

        val json = JSONObject(loadJSONFromAsset())
        val destinosJsonArray = json.getJSONArray("destinos")
        val nombreDestinos = ArrayList<String>()

        if(!filtroSeleccionado.isNullOrEmpty()) {
            filterByCategory(filtroSeleccionado, destinosJsonArray, nombreDestinos)
        }

        fillList(destinosJsonArray, nombreDestinos)
    }

    //Filtar por categoria
    fun filterByCategory(filtroSeleccionado:String, destinosJsonArray:JSONArray, nombreDestinos:ArrayList<String>){
        for (i in 0 until destinosJsonArray.length()) {
            val jsonObject = destinosJsonArray.getJSONObject(i)
            val nombre = jsonObject.getString("nombre")
            val categoria = jsonObject.getString("categoria").trim().uppercase();
            if (categoria.equals(filtroSeleccionado.trim().uppercase()) || filtroSeleccionado == "Todos") {
                nombreDestinos.add(nombre)
            }
        }
    }

    fun fillList(destinosJsonArray:JSONArray, nombreDestinos:ArrayList<String>){
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombreDestinos)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView , view, position, id ->
            val intent = Intent(this, DetallesDestinoActivity::class.java)
            intent.putExtra("destinoSeleccionado", findDestination(nombreDestinos[position], destinosJsonArray))
            startActivity(intent)
        }
    }

    //Leer JSON
    fun loadJSONFromAsset(): String?{
        var json: String? = null
        try {
            val istream: InputStream = assets.open("destinos.json")
            val size: Int = istream.available()
            val buffer = ByteArray(size)
            istream.read(buffer)
            istream.close()
            json = String(buffer, Charsets.UTF_8)
        }
        catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun findDestination(nombre: String, destinosJsonArray : JSONArray): String?{
        for(i in 0 until destinosJsonArray.length()){
            val jsonObject = destinosJsonArray.getJSONObject(i)
            val nombreJSON = jsonObject.getString("nombre")
            if(nombre == nombreJSON){
                val gson = Gson()
                val paisJSON = jsonObject.getString("pais")
                val categoriaJSON = jsonObject.getString("categoria")
                val planJSON = jsonObject.getString("plan")
                val precioJSON = jsonObject.getString("precio").toInt()
                val destino = Destino(0,nombreJSON,paisJSON,categoriaJSON,planJSON,precioJSON)
                val json = gson.toJson(destino)
                return json;
            }
        }
        return ""
    }
}