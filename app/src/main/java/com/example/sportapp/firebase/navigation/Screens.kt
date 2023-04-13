package com.example.sportapp.firebase.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object HomeScreen: Screens(route = "Home")
    object PerformanceScreen: Screens(route = "PerformanceScreen")
    object AddPerformanceScreen: Screens(route = "AddPerformanceScreen")
}
