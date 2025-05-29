package com.github.artemo24.dyrbok.updateanimals

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObject
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.MediaItem
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.Pet
import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreImplementation
import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreInterface
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies
import com.github.artemo24.dyrbok.utilities.logging.Log
import com.github.artemo24.dyrbok.websitescraper.AnimalShelterWebsiteScraper
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.runBlocking
import java.io.FileInputStream
import java.util.Properties
import kotlin.reflect.KMutableProperty1


fun main() {
    UpdateAnimals().printUpdatedAnimals()
}


class UpdateAnimals {
    private val logTag = UpdateAnimals::class.simpleName
    private val animalShelterWebsiteAddress = "https://www.dierenasielleiden.nl/"

    fun printUpdatedAnimals() {
        runBlocking {
            val websiteScraper = AnimalShelterWebsiteScraper(animalShelterWebsiteAddress)
            val websiteSpeciesName = AnimalSpecies.RABBIT.websiteSpeciesName
            val animalNamesToAnimalOverviewItemsMap = websiteScraper.getAnimalNamesToAnimalOverviewItems(websiteSpeciesName)

            val firestore = initializeFirestore()

            val (animals, mediaItems) = getAnimalsAndMediaItems(firestore)
            Log.d(logTag, "Read ${animals.size} animals and ${mediaItems.size} media items from the database.")

            animalNamesToAnimalOverviewItemsMap.keys.sorted().forEach { animalName ->
                Log.d(logTag, "- $animalName")
            }
        }
    }

    private fun initializeFirestore(): FirestoreImplementation {
        val backupProperties = Properties()
        backupProperties.load(FileInputStream("/Users/freek/test/kotlin/DyrBok/etc/dyrbok-backup.properties"))

        val firebaseProjectId = backupProperties["firebaseProjectId"].toString()
        val applicationCredentialsJsonPath = backupProperties["applicationCredentialsJsonPath"].toString()

        val googleCredentials = GoogleCredentials.fromStream(FileInputStream(applicationCredentialsJsonPath))

        return FirestoreImplementation(firebaseProjectId, googleCredentials)
    }

    private fun getAnimalsAndMediaItems(firestore: FirestoreInterface): Pair<List<Pet>, List<MediaItem>> {
        val animals = readFirestoreObjects(
            firestore,
            collectionName = "pets",
            objectClass = Pet::class.java,
            objectIdProperty = Pet::pet_id
        )
        val mediaItems = readFirestoreObjects(
            firestore,
            collectionName = "media_items",
            objectClass = MediaItem::class.java,
            objectIdProperty = MediaItem::media_item_id
        )

        return Pair(animals, mediaItems)
    }

    private inline fun <reified T : FirestoreObject> readFirestoreObjects(
        firestore: FirestoreInterface,
        collectionName: String,
        objectClass: Class<T>,
        objectIdProperty: KMutableProperty1<T, String>,
    ): List<T> =
        firestore
            .readFirestoreObjects(collectionName, objectClass, objectIdProperty)
            .sortedBy { it }
}
