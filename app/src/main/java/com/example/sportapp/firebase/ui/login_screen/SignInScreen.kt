package com.example.sportapp.firebase.ui.login_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlin.math.sign


@Composable
fun SignInScreen(
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
    onNavToSeancePage:() -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {


    // Initialisation de l'instance Firebase Auth
    val auth = Firebase.auth

    // Authentification de l'utilisateur de manière anonyme
    fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // L'utilisateur a été authentifié avec succès
                    val user = auth.currentUser
                }
            }
    }

    val googleSignInState = viewModel.googleState.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        }


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter your Email and Password to sign in",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .width(190.dp)
        )
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.width(280.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Blue,
                disabledLabelColor = Color.Blue, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ), shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.width(280.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Blue,
                disabledLabelColor = Color.Blue, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ), shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Password")
            }
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
                    if (auth.currentUser != null) {
                        onNavToHomePage.invoke()
                    }
                }
            },
            modifier = Modifier
                .width(125.dp)
                .height(63.dp)
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White, contentColor = Color.Black
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(text = "Sign In", color = Color.Black, modifier = Modifier.padding(0.dp))
        }

        TextButton(onClick = {
            scope.launch {
                signInAnonymously()
                if (auth.currentUser != null) {
                    onNavToHomePage.invoke()
                }
            }
        }) {
            Text(
                text = "Continue without signing in",
                color = Color.White
            )
        }

        Button(onClick = {
            scope.launch {
                signInAnonymously()
                if (auth.currentUser != null) {
                    onNavToSeancePage.invoke()
                }
            }
        }) {
            Text(
                text = "Seance Page",
                color = Color.White
            )
        }




        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value is SignInState.Loading) {
                CircularProgressIndicator()
            }
        }
        Text(
            text = "New User? Sign Up ",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(15.dp)
                .clickable {
                    onNavToSignUpPage.invoke()
                },
        )
        Text(
            text = "Or connect with",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken("809261993757-3kc4hri7t56o5foj6rnuo5osnj8smo1u.apps.googleusercontent.com")
                    .build()

                val googleSingInClient = GoogleSignIn.getClient(context, gso)

                launcher.launch(googleSingInClient.signInIntent)

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_black),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }

            LaunchedEffect(key1 = state.value is SignInState.Success) {
                scope.launch {
                    if (state.value is SignInState.Success) {
                        val success = state.value is SignInState.Success
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            LaunchedEffect(key1 = state.value is SignInState.Error) {
                scope.launch {
                    if (state.value is SignInState.Error) {
                        val error = (state.value as SignInState.Error)?.error
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            LaunchedEffect(key1 = googleSignInState.success) {
                scope.launch {
                    if (googleSignInState.success != null) {
                        Toast.makeText(context, "Sign In Success", Toast.LENGTH_LONG).show()
                        onNavToHomePage.invoke()
                    }
                }
            }

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (googleSignInState.loading) {
                CircularProgressIndicator()
            }
        }
    }
}


