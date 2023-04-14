package com.example.sportapp.feature.add_session.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.feature.add_session.data.model.Exercise
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.google.firebase.Timestamp

@Composable
fun AddSportSessionScreen(viewModel: SportSessionViewModel) {

    val nameState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val exerciseNameState = remember { mutableStateOf("") }
    val setsState = remember { mutableStateOf("") }
    val repetitionsState = remember { mutableStateOf("") }
    val weightState = remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = dateState.value,
                onValueChange = { dateState.value = it },
                label = { Text("Date") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = exerciseNameState.value,
                onValueChange = { exerciseNameState.value = it },
                label = { Text("Exercise Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = setsState.value,
                onValueChange = { setsState.value = it },
                label = { Text("Sets") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = repetitionsState.value,
                onValueChange = { repetitionsState.value = it },
                label = { Text("Repetitions") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = weightState.value,
                onValueChange = { weightState.value = it },
                label = { Text("Weight") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val exercise = Exercise(
                        name = exerciseNameState.value,
                        numberOfSets = setsState.value.toIntOrNull() ?: 0,
                        numberOfRepetitions = repetitionsState.value.toIntOrNull() ?: 0,
                        weight = weightState.value.toDoubleOrNull() ?: 0.0
                    )
                    viewModel.addSportSession(
                        name = nameState.value,
                        date = Timestamp.now(),
                        exercises = listOf(exercise)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Sport Session")
            }
        }

        val state by viewModel.state.collectAsState()

        val context = LocalContext.current

        when (state) {
            is AddSportSessionState.Idle -> {
            }
            is AddSportSessionState.Loading -> {
                CircularProgressIndicator()
            }
            is AddSportSessionState.Success -> {
                showToast(context, "Success")
            }
            is AddSportSessionState.Error -> {
                showToast(context, "Error")
            }
        }
    }
}
fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}




