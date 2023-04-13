package com.example.sportapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportapp.firebase.ui.login_screen.SignInViewModel
import com.example.sportapp.firebase.ui.signup_screen.SignUpViewModel
import com.example.sportapp.firestore.FirestoreViewModel
import com.example.sportapp.firestore.PerformanceViewModel
import com.example.sportapp.firestore.seance.SeanceScreenViewModel
import com.example.sportapp.firestore.seance.SeanceViewModel
import com.example.sportapp.navigation.NavigationSetup
import com.example.sportapp.ui.theme.SportAppTheme
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.EmailAuthProvider.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val signInViewModel = viewModel(modelClass = SignInViewModel::class.java)
            val performanceViewModel = viewModel(modelClass = PerformanceViewModel::class.java)
            val firestoreViewModel = viewModel(modelClass = FirestoreViewModel::class.java)
            val signUpViewModel = viewModel(modelClass = SignUpViewModel::class.java)
            val seanceViewModel = viewModel(modelClass = SeanceViewModel::class.java)
            val seanceScreenViewModel = viewModel(modelClass = SeanceScreenViewModel::class.java)
                SportAppTheme {
                    NavigationSetup(
                        signInViewModel = signInViewModel,
                        signUpViewModel = signUpViewModel,
                        firestoreViewModel = firestoreViewModel,
                        performanceViewModel = performanceViewModel,
                        seanceViewModel = seanceViewModel,
                        seanceScreenViewModel = seanceScreenViewModel
                    ) }
        }
    }

}
