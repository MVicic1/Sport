package com.example.sportapp.feature.add_session.ui.model.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sportapp.feature.add_session.adapteur.SportSessionViewModel
import com.example.sportapp.firestore.Exercices
import com.example.sportapp.firestore.seance.SeanceViewModel
import com.example.sportapp.screens.NumberPicker

@Composable
fun ExerciceItem(exercice: Exercices, sportSessionViewModel: SportSessionViewModel) {


    /*
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = sportSessionViewModel.,
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
    }*/
}