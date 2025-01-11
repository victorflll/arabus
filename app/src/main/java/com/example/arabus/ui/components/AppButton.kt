package com.example.arabus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppGreen
import com.example.arabus.ui.theme.AppGreenOpacity
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite


@Preview
@Composable
private fun Preview(){
    AppButton(
        title = "Title",
        onClick = { println("Button clicked") }
    )
}

@Composable
fun AppButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = AppGreen,
            contentColor = AppWhite,
            disabledContainerColor = AppGrey,
            disabledContentColor = AppGreenOpacity
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
    ) {
        Text(text = title, fontSize = TextUnit(16f, TextUnitType.Sp))
    }
}
