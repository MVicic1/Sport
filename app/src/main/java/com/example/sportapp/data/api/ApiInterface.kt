package com.example.sportapp.data.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @Headers("X-Api-Key: 0Wcp1yYPaY7MIvbBl6ArQA==4nOJej9Egy1ziqg2")
    @GET("exercises")
    suspend fun getExercice(@Query("muscle") muscle: String,): List<Exercise>
}