package com.example.sportapp.firestore.seance

import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sportapp.firestore.Exercices
import com.google.firebase.auth.FirebaseUser


class SeanceViewModel (
    private val repository: SeanceRepository = SeanceRepository()
): ViewModel() {

    var seanceUiState by mutableStateOf(SeanceUiState(date = "", exercice = emptyList()))
        private set

    private val hasUser:Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onSeanceChange(seance: String){
        seanceUiState = seanceUiState.copy(seance = seance)
    }

    fun onExerciceChange(exercice: List<Exercices>){
        seanceUiState = seanceUiState.copy(exercice = exercice)
    }

    fun onSeanceTimeChange(date: String){
        seanceUiState = seanceUiState.copy(date = date)
    }


    fun addSeance(){
        if(hasUser){
            repository.addSeance(
                userId = user!!.uid,
                name = seanceUiState.seance,
                date = seanceUiState.date,
                exercices = seanceUiState.exercice
            ) {
                seanceUiState = seanceUiState.copy(seanceAddedStatus = it)
            }
        }
    }

    fun setEditFields(seance : Seances) {
        seanceUiState = seanceUiState.copy(
            seance = seance.name,
            date = seance.date
        )
    }

    fun getSeance(seanceId: String){
        repository.getSeances(
            seanceId = seanceId,
            onError = {}
        ) {
            seanceUiState = seanceUiState.copy(selectedSeance = it)
            seanceUiState.selectedSeance?.let { it -> setEditFields(it) }
        }
    }

    fun updateSeance(seanceId: String) {
        repository.updateSeance(
            name = seanceUiState.seance,
            seanceId = seanceId,
            exercices = Exercices()
        ) {
            seanceUiState = seanceUiState.copy(updateSeanceStatus = it)
        }
    }

    fun resetSeanceAddedStatus(){
        seanceUiState = seanceUiState.copy(
            seanceAddedStatus = false,
            updateSeanceStatus = false)
    }

    fun resetState(){
        seanceUiState = SeanceUiState(date = "", exercice = emptyList())
    }


    /* private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    @RequiresApi(Build.VERSION_CODES.O)
    fun createSeance(name: String, date: String, exercices: List<Exercices>) {
        val seanceRef = firestore.collection("Seances").document()
        val seance = Seances(
            userId = auth.currentUser?.uid ?: "",
            name = name,
            date = date,
            seanceId = seanceRef.id,
            exercices = exercices
        )
        seanceRef.set(seance)
    }

     */
}




data class SeanceUiState(
    val seance:String = "",
    val date: String = "",
    val exercice: List<Exercices>,
    val seanceAddedStatus:Boolean = false,
    val updateSeanceStatus:Boolean = false,
    val selectedSeance: Seances? = null
)





        /* val repositoryBis: StorageRepository = StorageRepository()
            if(hasUser){
                repositoryBis.addExercice(
                    userId = user!!.uid,
                    name = exercice.name,
                    performanceNumber = exercice.performanceNumber
                ) {
                    seanceUiState = seanceUiState.copy(seanceAddedStatus = it)
                }
            } */
