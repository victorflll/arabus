package com.example.arabus.ui.screens

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

suspend fun getRoute(start: LatLng, end: LatLng, context: Context): Triple<List<LatLng>, String?, String?> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=${start.latitude},${start.longitude}" +
                "&destination=${end.latitude},${end.longitude}" +
                "&mode=driving&key=APIKEY"

        val request = Request.Builder().url(url).build()
        try {
            val response = client.newCall(request).execute()
            val jsonData = response.body?.string() ?: ""
            val routesArray = JSONObject(jsonData).optJSONArray("routes") ?: return@withContext Triple(emptyList(), null, null)

            if (routesArray.length() > 0) {
                val route = routesArray.getJSONObject(0)
                val overviewPolyline = route.getJSONObject("overview_polyline").getString("points")
                val polyline = decodePolyline(overviewPolyline)

                val leg = route.getJSONArray("legs").getJSONObject(0)
                val duration = leg.getJSONObject("duration")
                val estimatedTime = duration.getString("text")
                val distance = leg.getJSONObject("distance").getString("text")

                return@withContext Triple(polyline, estimatedTime, distance)
            }
        } catch (_: Exception) {}
        Triple(emptyList(), null, null)
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
