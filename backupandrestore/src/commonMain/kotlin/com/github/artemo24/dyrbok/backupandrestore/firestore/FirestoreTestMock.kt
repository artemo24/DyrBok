package com.github.artemo24.dyrbok.backupandrestore.firestore

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObject
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.Logging
import com.google.cloud.Timestamp
import kotlin.reflect.KMutableProperty1


class FirestoreTestMock : FirestoreInterface {
    private val testData: Map<String, List<FirestoreObject>> = initializeTestData()

    private fun initializeTestData(): MutableMap<String, List<FirestoreObject>> {
        val testData = mutableMapOf<String, List<FirestoreObject>>()

        val logging = Logging()
        logging.logging_id = "123456"
        logging.date_time = Timestamp.parseTimestamp("2024-06-28T06:04:02.000Z")
        logging.logging_level = "info"
        logging.message = "New user 'Nicolette' with email address 'secret@gmail.com' asked for access as employee for Dierentehuis Stevenshage."
        logging.tag = "WelcomeToAppViewModel"
        logging.user_id = ""

        testData["logging"] = listOf(logging)

        return testData
    }

    fun getTestDataByCollectionName(collectionName: String): List<FirestoreObject> =
        testData[collectionName] ?: emptyList()

    override fun <T : FirestoreObject> readFirestoreObjects(
        collectionName: String, objectClass: Class<T>, objectIdProperty: KMutableProperty1<T, String>
    ): List<T> {
        @Suppress("UNCHECKED_CAST")
        return testData[collectionName] as? List<T> ?: emptyList()
    }
}
