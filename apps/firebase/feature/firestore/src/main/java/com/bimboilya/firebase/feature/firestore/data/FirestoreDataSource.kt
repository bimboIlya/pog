package com.bimboilya.firebase.feature.firestore.data

import com.bimboilya.firebase.feature.firestore.util.convertToObject
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val serializer: Json
) {

    private val firestore = Firebase.firestore

    suspend fun get(): List<Test> = withContext(Dispatchers.Default) {
        firestore
            .testCollection()
            .get().await()
            .map { it.convertToObject(serializer) }
    }

    suspend fun add(data: Test) {
        firestore
            .testCollection()
            .add(data).await()
    }

    private fun FirebaseFirestore.testCollection(): CollectionReference =
        collection("test")
}
