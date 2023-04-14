package com.example.sportapp.feature.add_session.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState

@Composable
fun AddSportSessionScreen() {
    val viewModel = remember { SportSessionViewModel() }
    Content(viewModel.state.collectAsState().value)
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
            is AddSportSessionState.Loading -> LoadingScreen()
            is AddSportSessionState.Success -> ReadyScreen()
            is AddSportSessionState.Error -> ErrorScreen()
        }
    }
}