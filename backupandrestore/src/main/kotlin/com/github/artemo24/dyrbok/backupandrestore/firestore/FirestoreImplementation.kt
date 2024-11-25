package com.github.artemo24.dyrbok.backupandrestore.firestore

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObject
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import kotlin.reflect.KMutableProperty1


class FirestoreImplementation(private val firebaseProjectId: String, private val googleCredentials: GoogleCredentials) : FirestoreInterface {
    // private lateinit var firestore: Firestore
    private val firestore = initialize()

    fun initialize(): Firestore {
        val firestoreOptions =
            FirestoreOptions
                .getDefaultInstance()
                .toBuilder()
                .setProjectId(firebaseProjectId)
                .setCredentials(googleCredentials)
                .build()

        return firestoreOptions.service
    }

    override fun <T : FirestoreObject> readFirestoreObjects(
        collectionName: String, objectClass: Class<T>, objectIdProperty: KMutableProperty1<T, String>
    ): List<T> {
        val querySnapshotFuture = firestore.collection(collectionName).get()

        return querySnapshotFuture
            .get()  // Future.get() blocks on response.
            .documents
            .map { document -> (document.toObject(objectClass) as T).apply { objectIdProperty.set(this, document.id) } }
    }
}
