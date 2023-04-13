package com.example.sportapp.screens


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sportapp.firebase.ui.login_screen.SignInViewModel
import com.example.sportapp.firebase.util.Resource
import com.example.sportapp.firestore.ExerciceUiState
import com.example.sportapp.firestore.Exercice
import com.example.sportapp.firestore.PerformanceViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PerformanceScreen(
    performanceViewModel: PerformanceViewModel?,
    signInViewModel: SignInViewModel?,
    onExerciceClick: (id:String) -> Unit,
    navToAddPerformancePage:() -> Unit,
    navToLoginPage:() -> Unit,
) {

    val exerciseUiState = performanceViewModel?.exerciseUiState ?: ExerciceUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedExercice: Exercice? by remember {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        performanceViewModel?.loadExercices()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navToAddPerformancePage.invoke() }, contentColor = Color.White, backgroundColor = Color.Black) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )

            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        signInViewModel?.signOut()
                        navToLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = "Home Page")
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            when(exerciseUiState.exerciseList){
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items (
                            exerciseUiState.exerciseList.data ?: emptyList()
                                ) { exercise ->
                            ExerciseItem(
                                exercice = exercise,
                                onLongClick = {
                                    openDialog = true
                                    selectedExercice = exercise
                                },
                            ) {
                                onExerciceClick.invoke(exercise.exerciceId)
                            }
                        }
                    }

                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = { Text(text = "Delete Exercice?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedExercice?.exerciceId?.let {
                                            performanceViewModel?.deleteExercice(it)
                                        }
                                        openDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red
                                    ),
                                ) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            }
                        )
                    }
                }


                else -> {
                    Text(text = exerciseUiState
                        .exerciseList.message?: "Unknown Error")
                }
            }
        }

    }

    LaunchedEffect(key1 = performanceViewModel?.hasUser) {
        if (performanceViewModel?.hasUser == false) {
            navToLoginPage.invoke()
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseItem(
    exercice: Exercice,
    onLongClick:() -> Unit,
    onClick:() -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    ) {
        Column {
            Text(
                text = "Exercice : " + exercice.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
                )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Weight : " + exercice.performanceNumber.toString() + " kg",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )

        }
    }
}


