package com.example.sportapp.firestore.seance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.firebase.util.Resource
import kotlinx.coroutines.launch

class SeanceScreenViewModel(
    private val repository: SeanceRepository = SeanceRepository()
): ViewModel () {


    var seanceUiState by mutableStateOf(SeancesUiState())

    val user = repository.user()
    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    fun loadSeance(){
        if (hasUser) {
            if (userId.isNotBlank()){
                getUserSeances(userId)
            }
        } else {
            seanceUiState = seanceUiState.copy(seanceList = Resource.Error("User is not logged In"))
        }
    }

    private fun getUserSeances(userId:String) = viewModelScope.launch {
        repository.getUserSeances(userId).collect {
            seanceUiState = seanceUiState.copy(seanceList = it)
        }
    }

    fun deleteSeance(seanceId: String) = repository.deleteSeance(seanceId) {
        seanceUiState = seanceUiState.copy(seanceDeletedStatus = it)
    }

}

data class SeancesUiState(
    val seanceList: Resource<List<Seances>> = Resource.Loading(),
    val seanceDeletedStatus:Boolean = false
)

