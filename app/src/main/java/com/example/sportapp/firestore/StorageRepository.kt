package com.example.sportapp.firestore

import com.example.sportapp.firebase.util.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val EXERCICES_DOCUMENT_REF = "Seances"

class StorageRepository {

    fun user() = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val exercicesRef:CollectionReference = Firebase
        .firestore.collection(EXERCICES_DOCUMENT_REF)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserExercices(
        userId: String,
    ): Flow<Resource<List<Exercices>>> = callbackFlow {
        var snapshotStateListener:ListenerRegistration? = null

        try {
            snapshotStateListener = exercicesRef
                .whereEqualTo("userId", userId)
                .addSnapshotListener{ snapshot, e ->
                    val response = if(snapshot != null) {
                        val exercices = snapshot.toObjects(Exercices::class.java)
                        Resource.Success(data = exercices)
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

    fun getExercices(
        exerciceId:String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Exercices?) -> Unit
    ) {
        exercicesRef
            .document(exerciceId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Exercices::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addExercice(
        userId: String,
        name:String = "",
        performanceNumber:Int = 0,
        onComplete: (Boolean) -> Unit
    ) {

        val exerciceId = exercicesRef.document("Exercices").id
        val exercice = Exercices(name, performanceNumber, exerciceId)

        exercicesRef
            .document(exerciceId)
            .set(exercice)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }

    }

    fun deleteExercice(
        exerciceId: String,
        onComplete: (Boolean) -> Unit
    ) {
        exercicesRef.document(exerciceId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke((it.isSuccessful))
            }
    }

    fun updateExercice(
        name:String,
        performanceNumber: Int,
        exerciceId: String,
        onResult:(Boolean) -> Unit
    ){

        val updateData = hashMapOf<String,Any>(
            "name" to name,
            "performanceNumber" to performanceNumber,

        )

        exercicesRef.document(exerciceId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

}

