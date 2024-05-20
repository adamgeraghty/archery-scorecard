package com.adamgeraghty.scorecard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.adamgeraghty.scorecard.ui.components.CollapsibleLazyColumn
import com.adamgeraghty.scorecard.ui.components.CollapsibleSection
import timber.log.Timber

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        CollapsibleLazyColumn(
            sections = listOf(
                CollapsibleSection(
                    title = "Fruits A",
                    rows = listOf("Apple", "Apricots", "Avocado"),
                ),
                CollapsibleSection(
                    title = "Fruits B",
                    rows = listOf("Banana", "Blackberries", "Blueberries"),
                ),
                CollapsibleSection(
                    title = "Fruits C",
                    rows = listOf("Cherimoya", "Cantaloupe", "Cherries", "Clementine"),
                ),
            ),
            onRowClick = {
                Timber.d("Clicked $it")
            },
        )
    }
}

@Preview
@Composable
fun HomeScreen_Preview() {
    HomeScreen(navController = rememberNavController())
}
