package com.github.artemo24.dyrbok.backupandrestore.firestore

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObject
import kotlin.reflect.KMutableProperty1


interface FirestoreInterface {
    fun <T : FirestoreObject> readFirestoreObjects(
        collectionName: String, objectClass: Class<T>, objectIdProperty: KMutableProperty1<T, String>
    ): List<T>
}
