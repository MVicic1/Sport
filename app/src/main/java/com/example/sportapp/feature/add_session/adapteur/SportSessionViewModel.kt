package com.example.sportapp.feature.add_session.adapteur


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.feature.add_session.data.SportSessionRepository
import com.example.sportapp.feature.add_session.data.model.Exercise
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SportSessionViewModel(
    private val repository: SportSessionRepository = SportSessionRepository()
) : ViewModel() {

    private val mutableState = MutableStateFlow<AddSportSessionState>(AddSportSessionState.Loading)
    val state = mutableState.asStateFlow()

    fun addSportSession(
        name: String,
        date: Timestamp,
        exercises: List<Exercise>
    ) {
        val userId = repository.getUserId()

        viewModelScope.launch {
            mutableState.value = AddSportSessionState.Loading

            repository.addSportSession(userId, name, date, exercises) { isSuccess ->
                if (isSuccess) {
                    mutableState.value = AddSportSessionState.Success
                } else {
                    mutableState.value = AddSportSessionState.Error
                }
            }
        }
    }
}
