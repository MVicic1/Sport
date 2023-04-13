package com.example.sportapp.firestore.seance

import com.example.sportapp.firestore.Exercice


data class Seances(
    val userId: String = "",
    val name: String = "",
    val date: String = "",
    val seanceId: String = "",
    val exercices: List<Exercice>
) {
    constructor() : this("", "", "", "", emptyList())
}
