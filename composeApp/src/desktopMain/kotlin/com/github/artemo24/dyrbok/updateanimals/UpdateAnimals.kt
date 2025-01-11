package com.github.artemo24.dyrbok.updateanimals

import com.github.artemo24.dyrbok.utilities.logging.Log
import com.github.artemo24.dyrbok.websitescraper.AnimalShelterWebsiteScraper
import kotlinx.coroutines.runBlocking
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
            val webAnimalSpeciesName = "konijn"
            val animalNamesToAnimalOverviewItemsMap = websiteScraper.getAnimalNamesToAnimalOverviewItems(webAnimalSpeciesName)

            // getAnimalsAndMediaItems()

            animalNamesToAnimalOverviewItemsMap.keys.sorted().forEach { animalName ->
                Log.d(logTag, "- $animalName")
            }
        }
    }

    // todo: Move the backupandrestore code to the composeApp module/directory.

//    private fun getAnimalsAndMediaItems(firestore: FirestoreInterface) {
//        val pets = readFirestoreObjects(
//            firestore,
//            collectionName = "pets",
//            objectClass = Pet::class.java,
//            objectIdProperty = Pet::pet_id
//        )
//        val mediaItems = readFirestoreObjects(
//            firestore,
//            collectionName = "media_items",
//            objectClass = MediaItem::class.java,
//            objectIdProperty = MediaItem::media_item_id
//        )
//
//    }
//
//    private inline fun <reified T : FirestoreObject> readFirestoreObjects(
//        firestore: FirestoreInterface,
//        collectionName: String,
//        objectClass: Class<T>,
//        objectIdProperty: KMutableProperty1<T, String>,
//    ): List<T> =
//        firestore.readFirestoreObjects(collectionName, objectClass, objectIdProperty)
//            .sortedBy { it }
}
