package com.example.sportapp.firestore.seance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sportapp.firestore.Exercice
import com.google.firebase.auth.FirebaseUser
import kotlin.random.Random


class SeanceViewModel (
    private val repository: SeanceRepository = SeanceRepository()
): ViewModel() {

    var seanceUiState by mutableStateOf(SeanceUiState(date = "", exerciceList = mutableListOf()))
        private set

    private val hasUser:Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onSeanceChange(seance: String){
        seanceUiState = seanceUiState.copy(seance = seance)
    }

    fun onAddExercice(){
        val exerciceList = seanceUiState.exerciceList
        exerciceList.add(Exercice())
        seanceUiState = seanceUiState.copy(exerciceList = exerciceList)
    }

    fun onSeanceTimeChange(date: String){
        seanceUiState = seanceUiState.copy(date = date)
    }

    fun onExerciceNameChange(exercice: Exercice, newName: String) {
        val newExercice = exercice.copy(name = newName)
        val index = seanceUiState.exerciceList.indexOf(exercice)
        if (index != -1) {
            seanceUiState.exerciceList[index] = newExercice
            seanceUiState = seanceUiState.copy()
        }
    }

    fun onExercicePerformanceNumberChange(exercice: Exercice, newNumber: Int) {
        val newExercice = exercice.copy(performanceNumber = newNumber)
        val index = seanceUiState.exerciceList.indexOf(exercice)
        if (index != -1) {
            seanceUiState.exerciceList[index] = newExercice
            seanceUiState = seanceUiState.copy()
        }
    }


    fun addSeance(){
        if(hasUser){
            repository.addSeance(
                userId = user!!.uid,
                name = seanceUiState.seance,
                date = seanceUiState.date,
                exercices = seanceUiState.exerciceList
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
            exercice = Exercice()
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
        seanceUiState = SeanceUiState(date = "", exerciceList = mutableListOf())
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
    /**
     * en règle général pour que tu évites de t'embrouiller avec les "s"
     * là ton object Exercices c'est un exercice au singulier donc il
     * devrait s'appeler Exercice par contre la variable c'est une liste d'exos
     * là t'as deux choix ou la variable s'apelles :
     * - exercices : ce qui corrrespond à une liste d'exo
     * - exerciceList : hyper explicite, j'ai une préférence pour la première moi
     * mais ça pour le coup c'ets chacun ses gouts :)
     */
    val exerciceList: MutableList<Exercice>,
    val seanceAddedStatus:Boolean = false,
    val updateSeanceStatus:Boolean = false,
    val selectedSeance: Seances? = null
) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = Random(Int.MAX_VALUE).nextInt()
}





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
