package com.github.artemo24.dyrbok.analyzeusage

import com.github.artemo24.dyrbok.backupandrestore.main.DyrBokBackupAndRestore
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObjects
import com.github.artemo24.dyrbok.backupandrestore.utilities.StorageUtilities
import java.io.FileInputStream
import java.util.Properties


fun main() {
    AnalyzeUsage().analyzeUsage()
}


class AnalyzeUsage {
    private val unknownUser = "<<<Unknown user>>>"

    fun analyzeUsage() {
        println()

        // Disabled: analyzeAnimalsAndMediaItems()

        // Disabled: analyzeUsageLogging()

        // Disabled: analyzeAppVersions()

        analyzeAddMediaItems()
    }

    @Suppress("unused")
    fun analyzeAnimalsAndMediaItems() {
        val firestoreObjects = readFirestoreObjects()

        val animalsByAnimalSpecies = firestoreObjects.pets.groupBy { it.pet_type }

        // Validation:
        //
        // - Each animal ID is unique.
        // - todo: Each animal's unique name is unique.
        // - todo: Each animal is only registered with one animal species.
        // - The animal species (pet_type) should not be empty or equal to "unknown".
        //
        // - Each media item ID is unique.
        // - Each media item URL is unique and can be downloaded.
        // - Each media item is linked to exactly one animal.

        val animalIDsToAnimals = mutableMapOf<String, Any>()
        val mediaItemIDsToMediaItems = mutableMapOf<String, Any>()
        val mediaItemURLsToMediaItems = mutableMapOf<String, Any>()

        animalsByAnimalSpecies.keys.sorted().forEach { animalSpecies ->
            println("=== === === === === ===")
            println(animalSpecies)
            animalsByAnimalSpecies[animalSpecies]
                ?.sortedBy { it.getNameUnique() }
                ?.forEach { animal ->
                    println("- ${animal.getNameUnique()} (species: ${animal.pet_type}, ID: ${animal.pet_id})")

                    processUniqueField(animalIDsToAnimals, animal, animal.pet_id, objectDescription = "animal", uniqueFieldDescription = "ID")

                    if (animal.pet_type == "") {
                        println(">>> Unexpected empty animal species for animal with ID '${animal.pet_id}'.")
                    }

                    firestoreObjects.mediaItems.filter { it.pet_id == animal.pet_id }.forEach { mediaItem ->
                        println("  + media item with URL '${StorageUtilities.getShortMediaItemUrl(mediaItem)}'.")

                        processUniqueField(
                            mediaItemIDsToMediaItems,
                            mediaItem,
                            mediaItem.media_item_id,
                            objectDescription = "media item",
                            uniqueFieldDescription = "ID"
                        )

                        val mediaItemUrl = mediaItem.photoUrl()
                        if (mediaItemUrl != null) {
                            processUniqueField(
                                mediaItemURLsToMediaItems,
                                mediaItem,
                                mediaItemUrl,
                                objectDescription = "media item",
                                uniqueFieldDescription = "URL"
                            )

                            // todo: Check whether the media item can be downloaded.
                        } else {
                            println(">>> Media item $mediaItem does not have a valid media item URL.")
                        }
                    }
                }
            println()
        }
    }

    private fun processUniqueField(
        uniqueFieldsToObjects: MutableMap<String, Any>,
        theObject: Any,
        uniqueField: String,
        objectDescription: String,
        uniqueFieldDescription: String
    ) {
        if (uniqueFieldsToObjects.contains(uniqueField)) {
            println(">>> Duplicate $objectDescription $uniqueFieldDescription '$uniqueField'.")
            println(">>> Previous $objectDescription: ${uniqueFieldsToObjects[uniqueField]}.")
            println(">>> Current $objectDescription: ${theObject}.")
        } else {
            uniqueFieldsToObjects[uniqueField] = theObject
        }
    }

    @Suppress("unused")
    fun analyzeUsageLogging() {
        val firestoreObjects = readFirestoreObjects()

        val animalMap = firestoreObjects.pets.associateBy { it.pet_id }
        val usersMap = firestoreObjects.users.associateBy { it.user_id }
        val userAliases = usersMap.values
            .map { it.name }
            .sorted()
            .mapIndexed { userIndex, userName -> userName to "User ${userIndex + 1}" }
            .toMap()

        // We can later cross reference these changes by going through the other JSON files.
        val addMediaItemPrefix = "Add media item for shelter animal "
        val fromWebsiteBaseUrl = "https://www.dierenasielleiden.nl/wp-content/uploads/"

        firestoreObjects.logging.sortedDescending().forEach { logging ->
            val userName = usersMap[logging.user_id]?.name ?: unknownUser
            val userAlias = userAliases[userName] ?: unknownUser
            val message = logging.message.replace(userName, userAlias)

            if (message.contains(addMediaItemPrefix)) {
                val sourceIndicator = if (message.contains(fromWebsiteBaseUrl)) "(W)" else "   "
                val animalId = message.substringAfter(delimiter = "' (ID: ").substringBefore(delimiter = ") by '")
                val animalSpecies = animalMap[animalId]?.pet_type ?: "???"
                val enhancedMessage = "$addMediaItemPrefix($animalSpecies) ${message.substringAfter(delimiter = addMediaItemPrefix)}"

                println("${logging.date_time} ${userAlias.padStart(length = 20)} $sourceIndicator - $enhancedMessage")
            } else if (logging.message.contains("quiz")) {
                println("${logging.date_time} ${userAlias.padStart(length = 20)}     - $message")
            } else {
                // todo: Anonymize other types of messages. Search for all users names and email addresses in each
                //       logging message.
                println("${logging.date_time} ${userAlias.padStart(length = 20)}     - $message")
            }
        }

        println()
        println("(W): media item added from the website.")
    }

    @Suppress("unused")
    private fun analyzeAppVersions() {
        val firestoreObjects = readFirestoreObjects()

        val userAliases = firestoreObjects.users
            .map { it.firebase_user_id }
            .sorted()
            .mapIndexed { userIndex, firebaseUserId -> firebaseUserId to "User ${userIndex + 1}" }
            .toMap()

        val lastMessagePerUser = mutableMapOf<String, String>()

        firestoreObjects.screenSwitches
            .filter { screenSwitch -> screenSwitch.parameters.startsWith(prefix = "App version: ") }
            .forEach { screenSwitch ->
                val userAlias = userAliases[screenSwitch.firebase_user_id] ?: unknownUser
                val screenSwitchPart = "Screen switch on ${screenSwitch.date_time.take(19)} by $userAlias"
                val sourceAndDestinationPart = "from ${screenSwitch.source_screen_name} to ${screenSwitch.target_screen_name}"
                val parametersPart = if (screenSwitch.parameters.isEmpty()) "" else " with parameters '${screenSwitch.parameters}'"
                val message = "$screenSwitchPart ${sourceAndDestinationPart}$parametersPart"
                println(message)

                lastMessagePerUser[userAlias] = message
            }

        println()
        println("Last screen switches per user:")
        lastMessagePerUser
            .keys
            .sortedBy { it.substringAfter("User ").toInt() }
            .forEach { userAlias ->
                println("- $userAlias: ${lastMessagePerUser[userAlias]}")
            }
    }

    private fun analyzeAddMediaItems() {
        val firestoreObjects = readFirestoreObjects()
        var websiteMediaItemCount = 88
        var firebaseMediaItemCount = 45
        var totalMediaItemCount = websiteMediaItemCount + firebaseMediaItemCount

        // Show details:
//        firestoreObjects.logging
//            .filter { it.message.startsWith("Add media item") }
//            .forEach {
//                mediaItemCount++
//                println("${it.date_time} ${mediaItemCount.toString().padStart(4)} ${it.message}")
//            }

        val filteredLoggingRecords = firestoreObjects.logging
            .filter { it.message.startsWith("Add media item") }

        var previousDate = filteredLoggingRecords.first().date_time.toString().take(10)
        filteredLoggingRecords
            .forEach {
                val date = it.date_time.toString().take(10)
                if (date != previousDate) {
                    println("$previousDate\t$totalMediaItemCount\t$websiteMediaItemCount\t$firebaseMediaItemCount")
                    previousDate = date
                }

                val firebaseStorage = it.message.contains("https://firebasestorage.googleapis.com")
                if (firebaseStorage) {
                    firebaseMediaItemCount++
                } else {
                    websiteMediaItemCount++
                }
                totalMediaItemCount++
            }

        println("${filteredLoggingRecords.last().date_time.toString().take(10)}\t$totalMediaItemCount\t$websiteMediaItemCount\t$firebaseMediaItemCount")
    }

    private fun readFirestoreObjects(): FirestoreObjects {
        val backupProperties = Properties()
        backupProperties.load(FileInputStream("/Users/freek/test/kotlin/DyrBok/etc/dyrbok-backup.properties"))
        val jsonFilesDirectory = backupProperties["outputDirectory"].toString()
        // Later: val photoBucketUrl = backupProperties["photoBucketUrl"].toString()

        return DyrBokBackupAndRestore().readObjectsFromJsonFiles(inputDirectory = jsonFilesDirectory)
    }
}
