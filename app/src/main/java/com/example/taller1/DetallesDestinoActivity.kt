package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.taller1.model.Destino
import com.example.taller1.model.WeatherResponse
import com.example.taller1.services.IWeatherService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallesDestinoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_destino)

        val intent = intent
        val destinoSeleccionado = intent.getStringExtra("destinoSeleccionado")
        val gson = Gson()
        val destino = gson.fromJson(destinoSeleccionado, Destino::class.java)

        findViewById<TextView>(R.id.nombre).text = destino.nombre
        findViewById<TextView>(R.id.pais).text = destino.pais
        findViewById<TextView>(R.id.categoria).text = destino.categoria
        findViewById<TextView>(R.id.plan).text = destino.plan
        findViewById<TextView>(R.id.precio).text = "USD " + destino.precio.toString()

        findViewById<Button>(R.id.favoritos).setOnClickListener {
            val destino = // Aquí obtienes el objeto Destino correspondiente
                MainActivity.destinosFavoritos.add(destino)
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            findViewById<Button>(R.id.favoritos).isEnabled = false
        }
        getWeather(destino.pais)

    }
    private fun getWeather(city: String) {
        val temperaturaTxt = findViewById<TextView>(R.id.temperatura)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IWeatherService::class.java)
        val call = service.getCurrentWeather(city, "7dc64ee5f1934d7cabe193311242702")

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    if (weatherData != null) {
                        temperaturaTxt.text = weatherData.current.temp_c.toString() + " °C"
                    }
                } else {
                    // Manejar casos de respuesta no exitosa, como errores 4xx/5xx
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Aquí manejas errores de red o problemas de serialización
            }
        })
    }
}