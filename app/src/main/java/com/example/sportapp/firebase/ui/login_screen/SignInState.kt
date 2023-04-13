package com.example.sportapp.firebase.ui.login_screen

sealed class SignInState {

    object Loading : SignInState()
    object Success : SignInState()
    data class Error(
        val error: String
    ) : SignInState()

}