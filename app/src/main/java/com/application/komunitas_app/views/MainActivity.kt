package com.application.komunitas_app.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.komunitas_app.data.viewmodel.KomunitasViewModel
import com.application.komunitas_app.navigation.Screen
import com.application.komunitas_app.ui.theme.DonationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DonationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val komunitasViewModel = KomunitasViewModel(application)
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.AddDonateScreen.route) {
                            AddMemberScreen(komunitasViewModel = komunitasViewModel)
                        }
                        composable(route = Screen.AllDonatesScreen.route) {
                            AllMembersScreen(
                                navController = navController,
                                komunitasViewModel = komunitasViewModel
                            )
                        }
                        composable(
                            route = Screen.DonateDetailsScreen.route + "/{community_id}",
                            arguments = listOf(
                                navArgument("community_id") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                    nullable = false
                                }
                            )
                        ) {
                            val id = it.arguments?.getInt("community_id") ?: -1
                            MemberDetailScreen(id, komunitasViewModel = komunitasViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}