package com.example.sportapp.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapp.firestore.Exercices
import com.example.sportapp.firestore.FirestoreViewModel
import com.example.sportapp.firestore.seance.SeanceUiState
import com.example.sportapp.firestore.seance.SeanceViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AddSeanceScreen(
    seanceViewModel: SeanceViewModel?,
    seanceId: String,
    onNavToHomePage: () -> Unit,
    firestoreViewModel : FirestoreViewModel?
) {

    val exercices = remember { mutableStateListOf<Exercices>() }

    val seanceUiState =
        seanceViewModel?.seanceUiState ?: SeanceUiState(date = "", exercice = exercices)

    val isFormsNotBlank = seanceUiState.seance.isNotBlank()

    val isSeanceIdNotBlank = seanceId.isNotBlank()
    val icon = if (isSeanceIdNotBlank) Icons.Default.Refresh
    else Icons.Default.Check
    LaunchedEffect(key1 = Unit) {
        if (isSeanceIdNotBlank) {
            seanceViewModel?.getSeance(seanceId)
        } else {
            seanceViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    @Composable
    fun ExerciceItem(exercice: Exercices, seanceViewModel: SeanceViewModel) {

        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = firestoreViewModel?.performanceUiState!!.exercice,
                onValueChange = {
                    firestoreViewModel?.onExerciceChange(it)},
                label = { Text(text = "Exercice") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            NumberPicker(
                value = firestoreViewModel?.performanceUiState!!.performance,
                onValueChange = { firestoreViewModel?.onPerformanceChange(it) })
        }
    }

    @Composable
    fun ExercicesList(exercices: MutableList<Exercices>) {
        Column {
            Text("Exercices")
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(exercices) { exercice ->
                    ExerciceItem(exercice = exercice, seanceViewModel = SeanceViewModel())
                }
            }

            Button(
                onClick = { exercices.add(Exercices()) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Ajouter un exercice")
            }
            Button(onClick = { firestoreViewModel?.addExercice() }) {
                Text(text = "validate exercice")
            }
        }
    }



    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isSeanceIdNotBlank) {
                        seanceViewModel?.updateSeance(seanceId)
                    } else {
                        // seanceViewModel?.createSeance(name.value, date.value, exercices)
                        seanceViewModel?.addSeance()
                    }
                },
                contentColor = Color.White, backgroundColor = Color.Black
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }, topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        onNavToHomePage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = "Add Exercices Page")
                }
            )
        }) {
        Column() {

            if (seanceUiState.seanceAddedStatus) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Added exercice")
                    seanceViewModel?.resetSeanceAddedStatus()
                    onNavToHomePage.invoke()
                }
            }

            if (seanceUiState.updateSeanceStatus) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Exercice updated")
                    seanceViewModel?.resetSeanceAddedStatus()
                    onNavToHomePage.invoke()
                }
            }


            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = seanceUiState.seance,
                    onValueChange = { seanceViewModel?.onSeanceChange(it)},
                    label = { Text("Nom de la séance") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = seanceUiState.date,
                    onValueChange = { seanceViewModel?.onSeanceTimeChange(it) },
                    label = { Text("Date de la séance") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExercicesList(exercices = exercices)
            }
        }
    }
}






@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { onValueChange(value - 1) }
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Remove")
        }

        Text(
            text = value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        IconButton(
            onClick = { onValueChange(value + 1) }
        ) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Add")
        }
    }
}
