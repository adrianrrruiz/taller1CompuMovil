package com.example.taller1.model

data class WeatherResponse(
    val current: Current,
    val location: Location
)

data class Location(
    val name: String
)

data class Current(
    val temp_c: Float
)



