package com.example.sportapp.firestore.seance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.sportapp.firestore.Exercices
import com.example.sportapp.firestore.FirestoreViewModel
import com.example.sportapp.navigation.SeanceRoutes

@Composable
fun SeanceScreen (
    seanceScreenViewModel: SeanceScreenViewModel,
    signInViewModel: SignInViewModel?,
    onSeanceClick: (id:String) -> Unit,
    navToAddSeancePage:() -> Unit,
    navToLoginPage:() -> Unit,
) {

    val seanceUiState = seanceScreenViewModel?.seanceUiState ?: SeancesUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedSeance: Seances? by remember {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        seanceScreenViewModel?.loadSeance()
    }


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navToAddSeancePage.invoke() }, contentColor = Color.White, backgroundColor = Color.Black) {
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
                    Text(text = "Seance Screen")
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            when(seanceUiState.seanceList){
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
                            seanceUiState.seanceList.data ?: emptyList()
                        ) { seance ->
                            SeanceItem(
                                seances = seance,
                                onLongClick = {
                                    openDialog = true
                                    selectedSeance = seance
                                },
                                onSeanceClick = { onSeanceClick.invoke(seance.seanceId) },
                                onClick =  { },
                                exercices = Exercices()
                            )
                        }
                    }

                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = { Text(text = "Delete Seance?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedSeance?.seanceId?.let {
                                            seanceScreenViewModel?.deleteSeance(it)
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
                    Text(text = seanceUiState
                        .seanceList.message?: "Unknown Error")
                }
            }
        }

    }

    LaunchedEffect(key1 = seanceScreenViewModel?.hasUser) {
        if (seanceScreenViewModel?.hasUser == false) {
            navToLoginPage.invoke()
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeanceItem(
    seances: Seances,
    exercices: Exercices,
    onLongClick:() -> Unit,
    onClick:() -> Unit,
    onSeanceClick: (id: String) -> Unit
) {


    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { isExpanded = !isExpanded }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Color.LightGray,
    ) {
            Column {
                if (isExpanded) {
                    Text(
                        text = "Seance : " + seances.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Date : " + seances.date,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Exercise : " + exercices.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Weight : " + exercices.performanceNumber,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )

                    Button(onClick = { onSeanceClick.invoke(seances.seanceId) },
                        modifier = Modifier
                            .width(125.dp)
                            .height(63.dp)
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White, contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(text = "Modify", color = Color.Black, modifier = Modifier.padding(0.dp))
                    }

                } else {
                    Text(
                        text = "Seance : " + seances.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
    }
}

@Composable
fun ExerciceList(exercices: MutableList<Exercices>) {
    Column {
        Text("Exercices")
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(exercices) { exercice ->
                exercice
            }
        }
    }
}

