package com.example.sportapp.feature.add_session.data

import com.example.sportapp.feature.add_session.data.model.Exercise
import com.example.sportapp.feature.add_session.data.model.SportSession
import com.example.sportapp.firebase.util.Resource
import com.example.sportapp.firestore.Exercices
import com.example.sportapp.firestore.seance.SEANCES_COLLECTION_REF
import com.example.sportapp.firestore.seance.Seances
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val SESSIONS_COLLECTION_REF = "Sessions"

class SportSessionRepository {


    fun user() = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val sessionRef: CollectionReference = Firebase
        .firestore.collection(SESSIONS_COLLECTION_REF)

    fun getSportSession(
        onError:(Throwable?) -> Unit,
        onSuccess: (List<SportSession?>) -> Unit
    ) {
        val userId = user()?.uid

        if (userId == null) {
            onError.invoke(java.lang.IllegalArgumentException("User Id null"))
            return
        }

        sessionRef
            .whereEqualTo("userId",userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObjects(SportSession::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addSportSession(
        userId:String,
        name:String,
        date: Timestamp,
        exercises: List<Exercise>,
        onComplete: (Boolean) -> Unit
    ) {

        val sportSessionID = sessionRef.document().id
        val sportSession = SportSession(userId, name, date, exercises)

        sessionRef
            .document(sportSessionID)
            .set(sportSession)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }
}