package com.example.sportapp.firestore.seance

import com.example.sportapp.firebase.util.Resource
import com.example.sportapp.firestore.Exercice
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val SEANCES_COLLECTION_REF = "Seances"

class SeanceRepository {

    fun user() = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val seanceRef: CollectionReference = Firebase
        .firestore.collection(SEANCES_COLLECTION_REF)


    fun getUserSeances(
        userId: String,
    ): Flow<Resource<List<Seances>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = seanceRef
                .whereEqualTo("userId", userId)
                .addSnapshotListener{ snapshot, e ->
                    val response = if(snapshot != null) {
                        val seances = snapshot.toObjects(Seances::class.java)
                        Resource.Success(data = seances)
                    } else {
                        Resource.Error("")
                    }
                    trySend(response)
                }
        } catch (e:Exception) {
            trySend(Resource.Error(""))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getSeances(
        seanceId:String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Seances?) -> Unit
    ) {
        seanceRef
            .document(seanceId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Seances::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addSeance(
        userId:String = "",
        name:String = "",
        date: String = "",
        exercices: List<Exercice>,
        onComplete: (Boolean) -> Unit
    ) {

        val seanceId = seanceRef.document().id
        val seance = Seances(userId, name, date, seanceId, exercices)

        seanceRef
            .document(seanceId)
            .set(seance)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }

    }

    fun deleteSeance(
        seanceId:String,
        onComplete: (Boolean) -> Unit
    ) {
        seanceRef.document(seanceId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke((it.isSuccessful))
            }
    }

    fun updateSeance(
        name:String = "",
        seanceId: String,
        exercice: Exercice,
        onResult:(Boolean) -> Unit
    ){

        val updateData = hashMapOf<String,Any>(
            "name" to name,
            "exercices" to exercice
        )

        seanceRef.document(seanceId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

}