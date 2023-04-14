package com.example.sportapp.feature.add_session.adapteur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.feature.add_session.data.SportSessionRepository
import com.example.sportapp.feature.add_session.data.model.SportSession
import com.example.sportapp.feature.add_session.ui.model.AddSportSessionState
import com.example.sportapp.firestore.Exercices
import com.example.sportapp.firestore.seance.SeanceRepository
import com.example.sportapp.firestore.seance.Seances
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SportSessionViewModel(
    private val repository: SportSessionRepository = SportSessionRepository()
) : ViewModel() {

    private val mutableState = MutableStateFlow<AddSportSessionState>(AddSportSessionState.Loading)
    val state = mutableState.asStateFlow()

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    init {
        viewModelScope.launch {
            delay(2_000)
            val sportSessions = listOf(
                SportSession("", "", "", Timestamp.now(), emptyList())
            )
            mutableState.value = AddSportSessionState.Success
        }
    }

}


data class SportSessionUiState(
    val sportSeance:String = "",
    val sportSeanceDate: String = "",
    val sportSeanceExercices: List<Exercices>,
    val sportSeanceAddedStatus:Boolean = false,
    val updateSportSeanceStatus:Boolean = false,
    val selectedSportSeance: Seances? = null
)
