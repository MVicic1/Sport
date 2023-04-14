package com.example.sportapp.feature.add_session.data.model

import com.google.firebase.Timestamp

data class SportSession(
    val userId: String = "",
    val name: String = "",
    val date: Timestamp,
    val exerciseList: List<Exercise>,
)
