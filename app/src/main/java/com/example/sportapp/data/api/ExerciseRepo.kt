package com.example.sportapp.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ExerciseRepo {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val exercisesApi: ApiInterface = Retrofit.Builder()
        .baseUrl(RetrofitInstance.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterface::class.java)

    suspend fun getExercises(muscle: String): List<Exercise> {
        return exercisesApi.getExercice(muscle)
    }
}