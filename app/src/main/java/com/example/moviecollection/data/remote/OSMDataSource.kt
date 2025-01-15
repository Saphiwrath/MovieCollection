package com.example.moviecollection.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OSMPlace (
    @SerialName("place_id")
    val id: Int,
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double,
    @SerialName("display_name")
    val displayName: String
)


class OSMDataSource (
    private val httpClient: HttpClient
) {
    private val baseUrl = "https://nominatim.openstreetmap.org"

    suspend fun searchByLatAndLon(lat: Double, lon: Double) : OSMPlace {
        val url = "$baseUrl/reverse?lat=${lat}&lon=${lon}&format=json&limit=1"
        return httpClient.get(url).body()
    }
}