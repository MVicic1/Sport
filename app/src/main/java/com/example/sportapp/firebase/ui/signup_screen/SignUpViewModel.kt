package com.example.sportapp.firebase.ui.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.firebase.data.AuthRepository
import com.example.sportapp.firebase.util.Resource
import com.example.sportapp.firebase.ui.login_screen.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signUpState  = Channel<SignInState>()
    val signUpState  = _signUpState.receiveAsFlow()


    fun registerUser(email:String, password:String) = viewModelScope.launch {
        repository.registerUser(email, password).collect{result ->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(SignInState.Success)
                }
                is Resource.Loading -> {
                    _signUpState.send(SignInState.Loading)
                }
                is Resource.Error -> {
                    _signUpState.send(SignInState.Error("${result.message}"))
                }
            }

        }
    }

}