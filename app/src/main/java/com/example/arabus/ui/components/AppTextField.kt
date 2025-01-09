package com.example.arabus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite


@Preview
@Composable
private fun Preview(){
    var text by remember { mutableStateOf("") }

    AppTextField(
        placeholder = "Placeholder",
        textState = text,
        onValueChange = { newText -> text = newText }
    )
}

@Composable
fun AppTextField(
    placeholder: String,
    textState: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = textState,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(AppWhite, shape = RoundedCornerShape(8.dp)),
        placeholder = { Text(text = placeholder, color = AppGrey) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}
