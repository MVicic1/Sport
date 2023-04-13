package com.example.sportapp.firebase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportapp.firebase.ui.login_screen.SignInScreen
import com.example.sportapp.firebase.ui.signup_screen.SignUpScreen
import com.example.sportapp.firestore.PerformanceViewModel
import com.example.sportapp.screens.Home
import com.example.sportapp.screens.PerformanceScreen

/* @Composable
fun NavigationGraph(navController: NavHostController = rememberNavController(), ) {
    NavHost(navController = navController, startDestination = Screens.PerformanceScreen.route) {

        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = Screens.HomeScreen.route) {
            Home(navController)
        }
        composable(route = Screens.PerformanceScreen.route) {
            PerformanceScreen(
                navController = navController,
                performanceViewModel = PerformanceViewModel(),
                onExerciceClick = { exerciseId ->
                    navController.navigate(
                        Screens.AddPerformanceScreen.route + "?id=$exerciseId"
                    ) {
                        launchSingleTop = true
                    }
                },
                navToAddPerformancePage = {
                    navController.navigate(Screens.AddPerformanceScreen.route)
                }) {
                navController.navigate(Screens.SignInScreen.route) {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                }

            }
        }
    }

} */