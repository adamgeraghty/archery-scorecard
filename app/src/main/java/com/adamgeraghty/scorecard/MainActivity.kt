package com.adamgeraghty.scorecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.adamgeraghty.scorecard.theme.ScorecardMaterialTheme
import com.adamgeraghty.scorecard.ui.navigation.ScorecardNavGraph
import com.adamgeraghty.scorecard.ui.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConfigureEdgeToEdgeWindow()

            ScorecardMaterialTheme {
                Surface(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
                    val navController = rememberNavController()
                    ScorecardNavGraph(
                        navController,
                        Screens.HomeScreen.route,
                    )
                }
            }
        }
    }

    /**
     * Enables edge to edge support of this activity.
     *
     * If you'd like, you can override the default behavior of system bars by
     * customizing the parameters in the call to [enableEdgeToEdge].
     */
    @Composable
    private fun ConfigureEdgeToEdgeWindow() {
        DisposableEffect(Unit) {
            enableEdgeToEdge()
            onDispose {}
        }
    }
}
