package com.adamgeraghty.app.ui.navigation

sealed class Screens(val route: String) {

    data object HomeScreen : Screens("home_screen")
}
