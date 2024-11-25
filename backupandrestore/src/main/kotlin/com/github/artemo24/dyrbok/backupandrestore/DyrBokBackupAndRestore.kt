package com.github.artemo24.dyrbok.backupandrestore

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObject
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.FirestoreObjects
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.Logging
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.MediaItem
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.Pet
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.ScreenSwitch
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.Settings
import com.github.artemo24.dyrbok.backupandrestore.dataclasses.User
import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreImplementation
import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreInterface
import com.github.artemo24.dyrbok.backupandrestore.utilities.StorageUtilities
import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import java.util.Properties
import kotlin.reflect.KMutableProperty1


fun main() {
    println()

    // Create a .properties file with the following content:
    val backupProperties = Properties()
    backupProperties.load(FileInputStream("etc/dyrbok-backup.properties"))
    val firebaseProjectId = backupProperties["firebaseProjectId"].toString()
    val googleServicesJsonPath = backupProperties["googleServicesJsonPath"].toString()
    val photoBucketUrl = backupProperties["photoBucketUrl"].toString()
    val outputDirectory = backupProperties["outputDirectory"].toString()

    StorageUtilities.setPhotoBucketUrl(photoBucketUrl)

    val googleCredentials = GoogleCredentials.fromStream(FileInputStream(googleServicesJsonPath))
    val firestore = FirestoreImplementation(firebaseProjectId, googleCredentials)

    println("Create a full backup of the Firestore database and the file storage of the Firebase project with ID '$firebaseProjectId'.")
    println("Output is written to directory '$outputDirectory'.")

    val backupAndRestore = DyrBokBackupAndRestore()
    backupAndRestore.createFullBackup(firestore, outputDirectory)
}


class DyrBokBackupAndRestore {
    private val verbose = true

    fun createFullBackup(firestore: FirestoreInterface, outputDirectory: String) {
        val firestoreObjects = readFirestoreDocumentsToObjects(firestore)
        writeObjectsToJsonFiles(firestoreObjects, outputDirectory)
        zipJsonFiles(outputDirectory)

        downloadPhotosFromFileStorage(firestoreObjects, outputDirectory)
    }

    fun readFirestoreDocumentsToObjects(firestore: FirestoreInterface): FirestoreObjects {
        val logging = readFirestoreObjects(
            firestore,
            collectionName = "logging",
            objectClass = Logging::class.java,
            objectIdProperty = Logging::logging_id
        )
        val pets = readFirestoreObjects(
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
        val users = readFirestoreObjects(
            firestore,
            collectionName = "user",
            objectClass = User::class.java,
            objectIdProperty = User::user_id
        )
        val screenSwitches = readFirestoreObjects(
            firestore,
            collectionName = "screen_switch",
            objectClass = ScreenSwitch::class.java,
            objectIdProperty = ScreenSwitch::screen_switch_id
        )
        val settings = readFirestoreObjects(
            firestore,
            collectionName = "setting",
            objectClass = Settings::class.java,
            objectIdProperty = Settings::setting_id
        )

        if (verbose) {
            printScreenSwitches(screenSwitches)
        }

        return FirestoreObjects(logging, mediaItems, pets, screenSwitches, settings, users)
    }

    private inline fun <reified T : FirestoreObject> readFirestoreObjects(
        firestore: FirestoreInterface,
        collectionName: String,
        objectClass: Class<T>,
        objectIdProperty: KMutableProperty1<T, String>,
    ): List<T> =
        firestore.readFirestoreObjects(collectionName, objectClass, objectIdProperty)
            .sortedBy { it }

    private fun printScreenSwitches(screenSwitches: List<ScreenSwitch>) {
        println()
        println("A total of ${screenSwitches.size} screen switches were found:")
        screenSwitches
            .groupBy { screenSwitch -> screenSwitch.email }
            .forEach { screenSwitchesPerUser ->
                println(
                    "- Screen switches for user with email address '${screenSwitchesPerUser.key}' " +
                            "(${screenSwitchesPerUser.value.size} switches):"
                )
                screenSwitchesPerUser.value.forEach { screenSwitch ->
                    println(
                        "  + ${screenSwitch.date_time} from ${screenSwitch.source_screen_name} to " +
                                "${screenSwitch.target_screen_name} (parameters: '${screenSwitch.parameters}')."
                    )
                }
            }
    }

    private fun writeObjectsToJsonFiles(firestoreObjects: FirestoreObjects, outputDirectory: String) {
        println("firestoreObjects: $firestoreObjects")
        println("outputDirectory: $outputDirectory")

        TODO("Write is not yet implemented")
    }

    private fun zipJsonFiles(outputDirectory: String) {
        println("outputDirectory: $outputDirectory")

        TODO("Zip is not yet implemented")
    }

    private fun downloadPhotosFromFileStorage(firestoreObjects: FirestoreObjects, outputDirectory: String) {
        println("firestoreObjects: $firestoreObjects")
        println("outputDirectory: $outputDirectory")

        TODO("Download is not yet implemented")
    }
}
