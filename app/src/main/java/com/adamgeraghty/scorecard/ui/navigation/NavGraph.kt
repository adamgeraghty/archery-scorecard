package com.adamgeraghty.scorecard.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adamgeraghty.scorecard.ui.screens.HomeScreen

@Composable
fun ScorecardNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Screens.HomeScreen.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
        ) {
            HomeScreen(navController = navController)
        }
    }
}

private const val NAV_DURATION = 300

private fun exitTransition() = slideOutHorizontally(
    targetOffsetX = { -NAV_DURATION },
    animationSpec = tween(
        durationMillis = NAV_DURATION,
        easing = FastOutSlowInEasing,
    ),
) + fadeOut(animationSpec = tween(NAV_DURATION))

private fun popEnterTransition() = slideInHorizontally(
    initialOffsetX = { -NAV_DURATION },
    animationSpec = tween(
        durationMillis = NAV_DURATION,
        easing= FastOutSlowInEasing
    )
)

