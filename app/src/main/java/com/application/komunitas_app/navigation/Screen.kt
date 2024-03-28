package com.application.komunitas_app.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("main_screen")
    object AddDonateScreen : Screen("add_a_donate")
    object DonateDetailsScreen : Screen("donate_details")
    object AllDonatesScreen : Screen("all_donate")
}
