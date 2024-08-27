package com.adamgeraghty.scorecard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.adamgeraghty.scorecard.ui.viewmodel.HomeViewModel
import androidx.navigation.compose.rememberNavController
import com.adamgeraghty.scorecard.ui.components.ArcheryPreview
import com.adamgeraghty.scorecard.ui.components.CollapsibleLazyColumn
import com.adamgeraghty.scorecard.ui.components.CollapsibleSection
import com.adamgeraghty.scorecard.ui.navigation.Screens
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Scores") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add Score") },
                icon = { Icon(Icons.Filled.Add, "Add Score") },
                onClick = {
                    navController.navigate(
                        Screens.TargetScoreScreen.route,
                    )
                },
                shape = RoundedCornerShape(16.dp),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            val sections = viewModel.sections.collectAsStateWithLifecycle().value
            CollapsibleLazyColumn(
                sections = sections.map { CollapsibleSection(it.title, it.rows) },
                onRowClick = {
                    Timber.d("Clicked $it")
//                navController.navigate("detailScreen/$it")
                    navController.navigate(Screens.CreateScorecardScreen.route)
                },
            )
        }
    }
}

@ArcheryPreview
@Composable
fun HomeScreen_Preview() {
    HomeScreen(navController = rememberNavController())
}
