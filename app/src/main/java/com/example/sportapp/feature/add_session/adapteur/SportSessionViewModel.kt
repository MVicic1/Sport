package com.example.sportapp.feature.add_session.adapteur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.feature.add_session.data.model.SportSession
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SportSessionViewModel : ViewModel() {

    private val mutableState = MutableStateFlow<AddSportSessionState>(AddSportSessionState.Loading)
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2_000)
            val sportSessions = listOf(
                SportSession("","","", Timestamp.now(), emptyList())
            )
            mutableState.value = AddSportSessionState.Success(sportSessions)
        }
    }

}
