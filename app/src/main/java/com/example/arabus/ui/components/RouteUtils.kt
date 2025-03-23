package com.example.arabus.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import com.google.maps.android.SphericalUtil


suspend fun getRoute(start: LatLng, end: LatLng, context: Context): Triple<List<LatLng>, String?, String?> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=${start.latitude},${start.longitude}" +
                "&destination=${end.latitude},${end.longitude}" +
                "&mode=driving&key=AIzaSyD8Afq5eFx6Lh7Ff__LB0cnv29GPD9_U6A"

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

fun getBusPositionAlongRoute(routePoints: List<LatLng>, fraction: Float): LatLng {
    val index = (fraction * (routePoints.size - 1)).toInt()
    return routePoints[index]
}

@SuppressLint("DefaultLocale")
fun calculateRemainingDistance(currentPosition: LatLng, remainingRoutePoints: List<LatLng>): String {
    var totalDistance = 0.0
    var lastPoint = currentPosition
    for (point in remainingRoutePoints) {
        totalDistance += SphericalUtil.computeDistanceBetween(lastPoint, point)
        lastPoint = point
    }
    return String.format("%.2f", totalDistance / 1000)
}

fun calculateRemainingTime(remainingDistance: String): String {
    val speed = 50
    val distance = remainingDistance.toDoubleOrNull() ?: 0.0
    val remainingTimeInHours = distance / speed
    val remainingTimeInMinutes = (remainingTimeInHours * 60).toInt()
    return "$remainingTimeInMinutes min"
}

fun getBusImageBitmap(context: Context, resourceId: Int): BitmapDescriptor {
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}