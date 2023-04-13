package com.example.sportapp.screens

import android.annotation.SuppressLint
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.sportapp.firestore.FirestoreViewModel
import com.example.sportapp.firestore.PerformanceUiState
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AddPerformanceScreen(
    firestoreViewModel: FirestoreViewModel?,
    exerciceId: String,
    onNavToHomePage: () -> Unit
) {

    val performanceUiState = firestoreViewModel?.performanceUiState?: PerformanceUiState()

    val isFormsNotBlank = performanceUiState.exercice.isNotBlank()

    val isExerciceIdNotBlank = exerciceId.isNotBlank()
    val icon = if (isExerciceIdNotBlank) Icons.Default.Refresh
    else Icons.Default.Check
    LaunchedEffect(key1 = Unit) {
        if (isExerciceIdNotBlank) {
            firestoreViewModel?.getExercice(exerciceId)
        } else {
            firestoreViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (isExerciceIdNotBlank) {
                        firestoreViewModel?.updateExercice(exerciceId)
                    } else {
                        firestoreViewModel?.addExercice()
                    }
                },
                contentColor = Color.White, backgroundColor = Color.Black){
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        , topBar = {
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
                Column (
                    modifier = Modifier
                        .height(160.dp)
                ){

                    if (performanceUiState.exerciceAddedStatus){
                        scope.launch {
                            scaffoldState.snackbarHostState
                                .showSnackbar("Added exercice")
                            firestoreViewModel?.resetExerciceAddedStatus()
                            onNavToHomePage.invoke()
                        }
                    }

                    if (performanceUiState.updateExerciceStatus) {
                        scope.launch {
                            scaffoldState.snackbarHostState
                                .showSnackbar("Exercice updated")
                            firestoreViewModel?.resetExerciceAddedStatus()
                            onNavToHomePage.invoke()
                        }
                    }

                    OutlinedTextField(
                        value = performanceUiState.exercice,
                        onValueChange = {
                            firestoreViewModel?.onExerciceChange(it)
                        },
                        label = { Text(text = "Exercice") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    /* NumberPicker(
                        value = performanceUiState.performance,
                        onValueChange = { firestoreViewModel?.onPerformanceChange(it)})
                } */

            }
        }}














