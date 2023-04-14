package com.example.sportapp.feature.add_session.ui

import android.se.omapi.Session
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.feature.add_session.data.SportSessionRepository
import com.example.sportapp.feature.add_session.data.model.Exercise
import com.example.sportapp.feature.add_session.data.model.SportSession
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.example.sportapp.feature.add_session.ui.model.component.ErrorScreen
import com.example.sportapp.feature.add_session.ui.model.component.LoadingScreen
import com.example.sportapp.feature.add_session.ui.model.component.ReadyScreen
import com.example.sportapp.firestore.seance.SeanceRepository
import com.google.firebase.Timestamp

@Composable
fun AddSportSessionScreen(viewModel: SportSessionViewModel) {
    /* val viewModel = remember { SportSessionViewModel() }
    Content(viewModel.state.collectAsState().value)*/

    val nameState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val exerciseNameState = remember { mutableStateOf("") }
    val setsState = remember { mutableStateOf("") }
    val repetitionsState = remember { mutableStateOf("") }
    val weightState = remember { mutableStateOf("") }

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
}


@Composable
fun Content(state: AddSportSessionState) {

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
        when (state) {
            is AddSportSessionState.Idle -> AddSportSessionScreen(SportSessionViewModel())
            is AddSportSessionState.Loading -> LoadingScreen()
            is AddSportSessionState.Success -> ReadyScreen()
            is AddSportSessionState.Error -> ErrorScreen()
        }
    }
}