package com.example.arabus.ui.utils

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class LoadAsset {
    companion object {
        @Composable
        fun PngExtension(
            path: String,
            width: Dp = 64.dp,
            height: Dp = 64.dp,
            description: String = "..."
        ) {
            val context = LocalContext.current
            val inputStream = context.assets.open("$path.png")
            val bitmap = BitmapFactory.decodeStream(inputStream)

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = description,
                modifier = Modifier.size(width = width, height = height),
            )
        }

        @Composable
        fun JpegExtension(
            path: String,
            width: Dp = 48.dp,
            height: Dp = 48.dp,
            description: String = "..."
        ) {
            val context = LocalContext.current
            val inputStream = context.assets.open("$path.jpeg")
            val bitmap = BitmapFactory.decodeStream(inputStream)

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = description,
                modifier = Modifier.size(width = width, height = height)
            )
        }
    }
}