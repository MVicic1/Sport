package com.example.sportapp.firestore.seance

import com.example.sportapp.firestore.Exercices


data class Seances(
    val userId: String = "",
    val name: String = "",
    val date: String = "",
    val seanceId: String = "",
    val exercices: List<Exercices>
) {
    constructor() : this("", "", "", "", emptyList())
}
