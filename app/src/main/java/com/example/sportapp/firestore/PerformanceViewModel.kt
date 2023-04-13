package com.example.sportapp.firestore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.sportapp.firebase.util.Resource
import kotlinx.coroutines.launch

class PerformanceViewModel(
    private val repository: StorageRepository = StorageRepository()
):ViewModel() {
    var exerciseUiState by mutableStateOf(ExerciceUiState())

    val user = repository.user()
    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    fun loadExercices(){
        if (hasUser) {
            if (userId.isNotBlank()){
                getUserExercices(userId)
            }
        } else {
            exerciseUiState = exerciseUiState.copy(exerciseList = Resource.Error("User is not logged In"))
        }
    }

    private fun getUserExercices(userId:String) = viewModelScope.launch {
        repository.getUserExercices(userId).collect {
            exerciseUiState = exerciseUiState.copy(exerciseList = it)
        }
    }

    fun deleteExercice(exerciceId: String) = repository.deleteExercice(exerciceId) {
        exerciseUiState = exerciseUiState.copy(exerciceDeletedStatus = it)
    }

}

data class ExerciceUiState(
    val exerciseList: Resource<List<Exercice>> = Resource.Loading(),
    val exerciceDeletedStatus:Boolean = false
)