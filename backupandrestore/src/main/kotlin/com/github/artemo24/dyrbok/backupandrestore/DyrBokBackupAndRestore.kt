package com.github.artemo24.dyrbok.backupandrestore


fun main() {
    println()

    val firebaseProjectId = "??????"

    val backupAndRestore = DyrBokBackupAndRestore()
    backupAndRestore.createFullBackup(firebaseProjectId)
}


class DyrBokBackupAndRestore {
    private val outputDirectory = "/home/freek/test/android/DyrBok/etc/DyrBokBackupAndRestore/output/"
    private val verbose = true

    fun createFullBackup(firebaseProjectId: String) {
        println("Create a full backup of the Firestore database and the file storage of the Firebase project with ID '$firebaseProjectId'...")

        val firestoreObjects = readFirestoreDocumentsToObjects(firebaseProjectId)

        writeObjectsToJsonFiles(firestoreObjects)
        zipJsonFiles()

        downloadPhotosFromFileStorage(firestoreObjects)
    }

    private fun readFirestoreDocumentsToObjects(firebaseProjectId: String): String {
        return "??????"
    }

    private fun writeObjectsToJsonFiles(firestoreObjects: String) {
        TODO("Not yet implemented")
    }

    private fun zipJsonFiles() {
    }

    private fun downloadPhotosFromFileStorage(firestoreObjects: String) {
    }
}
