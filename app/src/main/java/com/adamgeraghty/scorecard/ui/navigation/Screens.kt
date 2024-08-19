package com.adamgeraghty.scorecard.ui.navigation

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home_screen")

    data object CreateScorecardScreen : Screens("create_scorecard_screen")

    data object TargetScoreScreen : Screens("target_score_screen")
}
