package com.example.arabus.ui.screens

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

suspend fun getRoute(start: LatLng, end: LatLng, context: Context): List<LatLng> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=${start.latitude},${start.longitude}" +
                "&destination=${end.latitude},${end.longitude}" +
                "&mode=driving&key=AIzaSyB9Q3qkJoDYM8Cgc3nSWKXrrTaX6bvwDvk"

        val request = Request.Builder().url(url).build()
        try {
            val response = client.newCall(request).execute()
            val jsonData = response.body?.string() ?: ""
            val routesArray = JSONObject(jsonData).optJSONArray("routes") ?: return@withContext emptyList()
            if (routesArray.length() > 0) {
                val encodedPoints = routesArray.getJSONObject(0).getJSONObject("overview_polyline").getString("points")
                return@withContext decodePolyline(encodedPoints)
            }
        } catch (_: Exception) {}
        emptyList()
    }
}

fun decodePolyline(encoded: String): List<LatLng> {
    val poly = ArrayList<LatLng>()
    var index = 0
    val len = encoded.length
    var lat = 0
    var lng = 0

    while (index < len) {
        var shift = 0
        var result = 0
        do {
            val b = encoded[index++].code - 63
            result = result or (b and 0x1F shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
        lat += dlat

        shift = 0
        result = 0
        do {
            val b = encoded[index++].code - 63
            result = result or (b and 0x1F shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
        lng += dlng

        poly.add(LatLng(lat / 1E5, lng / 1E5))
    }
    return poly
}
