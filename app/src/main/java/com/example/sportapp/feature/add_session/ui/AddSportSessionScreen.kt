package com.example.sportapp.feature.add_session.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.feature.add_session.data.SportSessionRepository
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.example.sportapp.feature.add_session.ui.model.component.ErrorScreen
import com.example.sportapp.feature.add_session.ui.model.component.LoadingScreen
import com.example.sportapp.feature.add_session.ui.model.component.ReadyScreen
import com.example.sportapp.firestore.seance.SeanceRepository
import com.google.firebase.Timestamp

@Composable
fun AddSportSessionScreen() {
    val viewModel = remember { SportSessionViewModel() }
    Content(viewModel.state.collectAsState().value)

    Button(onClick = { viewModel.addSeance() }) {

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
            is AddSportSessionState.Idle -> AddSportSessionScreen()
            is AddSportSessionState.Loading -> LoadingScreen()
            is AddSportSessionState.Success -> ReadyScreen()
            is AddSportSessionState.Error -> ErrorScreen()
        }
    }
}