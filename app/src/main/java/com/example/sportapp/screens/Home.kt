package com.example.sportapp.screens


import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportapp.data.api.Exercise
import com.example.sportapp.data.api.ExerciseViewModel
import com.example.sportapp.firebase.navigation.Screens


@Composable
fun Home(
    navController: NavController,
) {


    // API display related
    val exerciseViewModel = viewModel(modelClass = ExerciseViewModel::class.java)
    val state by exerciseViewModel.state.collectAsState()

    // Search related
    val viewModel = viewModel<ExerciseViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun ExerciseCard(exercise: Exercise) {
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.background,
        )
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(8.dp)
        ) {
            Box {
                Surface(
                    color = surfaceColor,
                    contentColor = MaterialTheme.colors.primary,
                ) {
                    Column(
                        modifier = Modifier
                            .clickable { isExpanded = !isExpanded }
                            .padding(top = 10.dp, start = 18.dp)
                            .fillMaxWidth(),

                        ) {
                        if (isExpanded) {
                            Text(
                                text = "Exercise name : ${exercise.name}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(bottom = 6.dp)
                            )
                            Text(
                                text = "Exercise type : ${exercise.type}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp)
                            )
                            Text(
                                text = "Exercise muscle : ${exercise.muscle}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp)
                            )
                            Text(
                                text = "Exercise equipment : ${exercise.equipment}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp)
                            )
                            Text(
                                text = "Exercise difficulty : ${exercise.difficulty}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp)
                            )
                            Text(
                                text = "Exercise instructions : ${exercise.instructions}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 12.dp),
                            )
                        } else {
                            Text(
                                text = "Exercise name : ${exercise.name}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(bottom = 6.dp)
                            )

                        }
                    }
                    val isChecked = remember { mutableStateOf(false) }
                    Checkbox(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        checked = isChecked.value,
                        onCheckedChange = {
                            exercise.isChecked = it
                            isChecked.value = it
                        }
                    )
                    when (isChecked) {

                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hello User",
            fontSize = 50.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .align(Alignment.CenterHorizontally)
        )

        Button(onClick = { navController.navigate(Screens.PerformanceScreen.route) }) {
            Text(text = "Performance")
        }
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") },
        )
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                items(exercises) { exercise: Exercise ->
                    ExerciseCard(exercise = exercise)
                }
            }
        }
    }
}
