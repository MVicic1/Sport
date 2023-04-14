package com.example.sportapp.feature.add_session.data.model

data class Exercise (
    val name:String = "BenchPress",
    val numberOfSets: Int = 4,
    val numberOfRepetitions:Int = 12,
    val weight:Double = 125.00,
    val exerciseId: String = ""
)