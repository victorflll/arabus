package com.example.arabus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppGrey
import com.example.arabus.ui.theme.AppWhite
import androidx.compose.foundation.layout.width
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Street(
    val name: String,
    val latitude: Double,
    val longitude: Double
)
@Composable
fun AppSearchSelect(
    items: List<Street>,
    selectedItem: Street? = null,
    onSelect: (name: String, lat: Double, lng: Double) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf(selectedItem?.name ?: "") }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var filteredItems by remember { mutableStateOf<List<Street>>(emptyList()) }

    val density = LocalDensity.current
    val debounceScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    Column(modifier = modifier.padding(8.dp)) {
        OutlinedTextField(
            value = input,
            onValueChange = { newInput ->
                input = newInput
                debounceJob?.cancel()
                debounceJob = debounceScope.launch {
                    delay(800)
                    val result = items.filter { it.name.contains(newInput, ignoreCase = true) }
                    filteredItems = result
                    expanded = result.isNotEmpty() || newInput.isNotBlank()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .background(AppWhite, shape = RoundedCornerShape(8.dp)),
            placeholder = { Text(placeholder, color = AppGrey) },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(density) { textFieldSize.width.toDp() })
                .background(AppWhite)
        ) {
            if (filteredItems.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Nenhuma rua encontrada", color = AppGrey) },
                    onClick = {},
                    enabled = false
                )
            } else {
                filteredItems.forEach { street ->
                    DropdownMenuItem(
                        text = { Text(street.name) },
                        onClick = {
                            input = street.name
                            onSelect(street.name, street.latitude, street.longitude)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

