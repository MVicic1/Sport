package com.example.sportapp.firestore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class FirestoreViewModel(
    private val repository: StorageRepository = StorageRepository()
): ViewModel() {

    var performanceUiState by mutableStateOf(PerformanceUiState())
        private set

    private val hasUser:Boolean
        get() = repository.hasUser()

    private val user:FirebaseUser?
        get() = repository.user()


    fun onExerciceChange(exercice: String){
        performanceUiState = performanceUiState.copy(exercice = exercice)
    }

    fun onPerformanceChange(performance: Int){
        performanceUiState = performanceUiState.copy(performance = performance)
    }

    fun addExercice(){
        if(hasUser){
            repository.addExercice(
                userId = user!!.uid,
                name = performanceUiState.exercice,
                performanceNumber = performanceUiState.performance
            ) {
                performanceUiState = performanceUiState.copy(exerciceAddedStatus = it)
            }
        }
    }

    fun setEditFields(exercice: Exercice) {
        performanceUiState = performanceUiState.copy(
            exercice = exercice.name,
            performance = exercice.performanceNumber
        )
    }

    fun getExercice(exerciceId: String){
        repository.getExercices(
            exerciceId = exerciceId,
            onError = {}
        ) {
            performanceUiState = performanceUiState.copy(selectedExercice = it)
            performanceUiState.selectedExercice?.let { it -> setEditFields(it) }
        }
    }

    fun updateExercice(exerciceId: String) {
        repository.updateExercice(
            name = performanceUiState.exercice,
            exerciceId = exerciceId,
            performanceNumber = performanceUiState.performance
        ) {
            performanceUiState = performanceUiState.copy(updateExerciceStatus = it)
        }
    }

    fun resetExerciceAddedStatus(){
        performanceUiState = performanceUiState.copy(
            exerciceAddedStatus = false,
            updateExerciceStatus = false)
    }

    fun resetState(){
        performanceUiState = PerformanceUiState()
    }


}

data class PerformanceUiState(
    val exercice:String = "",
    val performance: Int = 0,
    val exerciceAddedStatus:Boolean = false,
    val updateExerciceStatus:Boolean = false,
    val selectedExercice:Exercice? = null
)