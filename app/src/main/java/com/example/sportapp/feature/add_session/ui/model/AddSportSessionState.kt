package com.example.sportapp.feature.add_session.ui.model

import com.example.sportapp.feature.add_session.data.model.SportSession

sealed class AddSportSessionState {
    object Loading : AddSportSessionState()
    data class Success(val sportSession: List<SportSession>) : AddSportSessionState()
    object Error : AddSportSessionState()
}
