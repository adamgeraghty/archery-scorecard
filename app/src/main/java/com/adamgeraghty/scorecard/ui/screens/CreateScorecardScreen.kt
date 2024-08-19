package com.adamgeraghty.scorecard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.adamgeraghty.scorecard.ui.components.SteppedSlider

@Composable
fun CreateScorecardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Scorecard")
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        SteppedSlider()
    }
}

@Preview
@Composable
private fun CreateScorecardScreen_Preview() {
    CreateScorecardScreen(navController = rememberNavController())
}
