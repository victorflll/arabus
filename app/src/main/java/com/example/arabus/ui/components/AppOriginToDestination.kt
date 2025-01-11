package com.example.arabus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arabus.ui.theme.AppWhite


@Preview
@Composable
private fun Preview(){
    AppOriginToDestination()
}

@Composable
fun AppOriginToDestination() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.MyLocation,
            contentDescription = "Search Icon",
            tint = AppWhite
        )
        VerticalDivider(
            modifier = Modifier.height(24.dp),
            thickness = 2.dp,
            color = AppWhite
        )
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = "Search Icon",
            tint = AppWhite
        )
    }
}
