package com.example.sportapp.screens


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportapp.data.api.Exercise
import com.example.sportapp.data.api.ExerciseViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen() {

    val viewModel = viewModel<ExerciseViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    var isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = if (isSheetFullScreen)
        Modifier
            .fillMaxSize()
    else
        Modifier.fillMaxWidth()

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(540.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange)
                Button(
                    onClick = {
                        coroutineScope.launch { modalSheetState.hide() }
                    }
                ) {
                    Text(text = "Hide Sheet")
                }
            }
        }
    ) {
        Scaffold {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 10.dp)
            ) {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp)
                        .height(120.dp)
                        .clickable {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    backgroundColor = Color.Gray
                ) {
                    Column() {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                                .width(60.dp)
                                .height(60.dp)
                        )
                        Text(
                            text = "Legs",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Card(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp)
                        .height(120.dp)
                        .clickable {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    backgroundColor = Color.Gray
                ) {
                    Column() {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                                .width(60.dp)
                                .height(60.dp)
                        )
                        Text(
                            text = "Chest",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Card(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp)
                        .height(120.dp)
                        .clickable {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    backgroundColor = Color.Gray
                ) {
                    Column() {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                                .width(60.dp)
                                .height(60.dp)
                        )
                        Text(
                            text = "Back",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}



