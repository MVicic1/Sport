package com.example.sportapp.feature.add_session.ui.model

import com.example.sportapp.feature.add_session.data.model.SportSession

sealed class AddSportSessionState {
    object Idle : AddSportSessionState()
    object Loading : AddSportSessionState()
    object Success : AddSportSessionState()
    object Error : AddSportSessionState()
}
