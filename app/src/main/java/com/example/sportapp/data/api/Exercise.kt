package com.example.sportapp.data.api

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exercise(
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "muscle")
    val muscle: String?,
    @Json(name = "equipment")
    val equipment: String?,
    @Json(name = "difficulty")
    val difficulty: String?,
    @Json(name = "instructions")
    val instructions: String?,
) {
    var isChecked: Boolean = false

    /* fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name",
            " $name",
            "${name?.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    } */

}

