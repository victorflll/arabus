package com.example.arabus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite

@Preview
@Composable
private fun Preview() {
    var text by remember { mutableStateOf("") }

    AppTextField(
        placeholder = "Placeholder",
        textState = text,
        onValueChange = { newText -> text = newText },
        isPassword = true,
        label = "Label Example",
        labelColor = Color.Red
    )
}

@Composable
fun AppTextField(
    placeholder: String,
    textState: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: String? = null,
    labelColor: Color = AppGrey
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(8.dp)) {
        if (label != null) {
            Text(
                text = label,
                color = labelColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        OutlinedTextField(
            value = textState,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(AppWhite, shape = RoundedCornerShape(8.dp)),
            placeholder = { Text(text = placeholder, color = AppGrey) },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}
