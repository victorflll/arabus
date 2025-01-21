package com.example.arabus.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
private fun Preview() {
    AppButton(
        title = "Title",
        onClick = { println("Button clicked") },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun AppButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit(16f, TextUnitType.Sp),
    padding: PaddingValues = PaddingValues(all = 16.dp)

) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppGreen,
            contentColor = AppWhite,
            disabledContainerColor = AppGrey,
            disabledContentColor = AppGreenOpacity
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
    ) {
        Text(text = title, fontSize = fontSize)
    }
}
