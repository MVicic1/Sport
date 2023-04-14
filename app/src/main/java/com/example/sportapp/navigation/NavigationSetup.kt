package com.example.sportapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.feature.add_session.ui.AddSportSessionScreen
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.example.sportapp.firebase.ui.login_screen.SignInScreen
import com.example.sportapp.firebase.ui.login_screen.SignInViewModel
import com.example.sportapp.firebase.ui.signup_screen.SignUpScreen
import com.example.sportapp.firebase.ui.signup_screen.SignUpViewModel
import com.example.sportapp.firestore.FirestoreViewModel
import com.example.sportapp.firestore.PerformanceViewModel
import com.example.sportapp.firestore.seance.SeanceScreen
import com.example.sportapp.firestore.seance.SeanceScreenViewModel
import com.example.sportapp.firestore.seance.SeanceViewModel
import com.example.sportapp.screens.AddPerformanceScreen
import com.example.sportapp.screens.AddSeanceScreen
import com.example.sportapp.screens.PerformanceScreen

enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home,
    Detail
}

enum class NestedRoutes {
    Main,
    Login
}

enum class SeanceRoutes {
    Seance,
    AddSeance
}


@Composable
fun NavigationSetup(
    navController: NavHostController = rememberNavController(),
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    firestoreViewModel: FirestoreViewModel,
    performanceViewModel: PerformanceViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NestedRoutes.Login.name
    ) {
        authGraph(
            navController,
            signInViewModel,
            signUpViewModel)
        homeGraph(
            navController = navController,
            firestoreViewModel,
            performanceViewModel,
            signInViewModel
        )
        seanceGraph(
        )
    }


}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel
) {
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            SignInScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                viewModel = signInViewModel,
                onNavToSignUpPage = {
                    navController.navigate(LoginRoutes.Signup.name) {
                        launchSingleTop = true
                        popUpTo(LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                onNavToSeancePage = {
                    navController.navigate(SeanceRoutes.Seance.name) {
                        launchSingleTop = true
                        popUpTo(SeanceRoutes.Seance.name) {
                            inclusive = true
                        }
                    }
                })
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(
                viewModel = signUpViewModel,
                onNavToLoginPage = {
                    navController.navigate(LoginRoutes.SignIn.name)
                })

        }

    }

}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    firestoreViewModel: FirestoreViewModel,
    performanceViewModel: PerformanceViewModel,
    signInViewModel: SignInViewModel
){
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ){
        composable(HomeRoutes.Home.name){
            PerformanceScreen(
                performanceViewModel = performanceViewModel,
                signInViewModel = signInViewModel,
                onExerciceClick = { exerciseId ->
                    navController.navigate(
                        HomeRoutes.Detail.name + "?id=$exerciseId"
                    ){
                        launchSingleTop = true
                    }
                },
                navToAddPerformancePage = {
                    navController.navigate(HomeRoutes.Detail.name)
                },
                navToLoginPage = {
                    navController.navigate(LoginRoutes.SignIn.name)
                }

            )
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
                defaultValue = ""
            })
        ){ entry ->

            AddPerformanceScreen(
                firestoreViewModel = firestoreViewModel,
                exerciceId = entry.arguments?.getString("id") as String,
                onNavToHomePage = {
                    navController.navigate(HomeRoutes.Home.name)
                }
            )
        }
    }
}

fun NavGraphBuilder.seanceGraph(
){
    navigation(
        startDestination = SeanceRoutes.Seance.name,
        route = NestedRoutes.Main.name,
    ){
        composable(SeanceRoutes.Seance.name){
            AddSportSessionScreen(
                viewModel = SportSessionViewModel(),
            )
        }

    }
}