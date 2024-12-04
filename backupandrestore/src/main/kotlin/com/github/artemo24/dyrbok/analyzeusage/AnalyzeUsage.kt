package com.github.artemo24.dyrbok.analyzeusage

import com.github.artemo24.dyrbok.backupandrestore.DyrBokBackupAndRestore
import java.io.FileInputStream
import java.util.Properties


fun main() {
    AnalyzeUsage().analyzeUsage()
}


class AnalyzeUsage {
    fun analyzeUsage() {
        println()

        val backupProperties = Properties()
        backupProperties.load(FileInputStream("etc/dyrbok-backup.properties"))
        // Later: val photoBucketUrl = backupProperties["photoBucketUrl"].toString()
        val jsonFilesDirectory = backupProperties["outputDirectory"].toString()

        val backupAndRestore = DyrBokBackupAndRestore()
        val firestoreObjects = backupAndRestore.readObjectsFromJsonFiles(inputDirectory = jsonFilesDirectory)

        val animalMap = firestoreObjects.pets.associateBy { it.pet_id }
        val usersMap = firestoreObjects.users.associateBy { it.user_id }
        val userAliases = usersMap.values.map { it.name }.sorted()
            .mapIndexed { userIndex, userName -> userName to "User ${userIndex + 1}" }.toMap()

        // We can later cross reference these changes by going through the other JSON files.
        val prefix = "Add media item for shelter animal "
        val fromWebsiteBaseUrl = "https://www.dierenasielleiden.nl/wp-content/uploads/"

        firestoreObjects.logging.sortedDescending().forEach { logging ->
            val userName = usersMap[logging.user_id]?.name ?: "<<<Unknown user>>>"
            val userAlias = (userAliases[userName] ?: "<<<Unknown user>>>")
            val message = logging.message.replace(userName, userAlias)

            if (message.contains(other = prefix)) {
                val sourceIndicator = if (message.contains(other = fromWebsiteBaseUrl)) "(W)" else "   "
                val animalId = message.substringAfter(delimiter = "' (ID: ").substringBefore(delimiter = ") by '")
                val animalSpecies = animalMap[animalId]?.pet_type ?: "???"
                val enhancedMessage = "$prefix($animalSpecies) ${message.substringAfter(delimiter = prefix)}"

                println("${logging.date_time} ${userAlias.padStart(length = 20)} $sourceIndicator - $enhancedMessage")
            } else if (logging.message.contains(other = "quiz")) {
                println("${logging.date_time} ${userAlias.padStart(length = 20)}     - $message")
            } // else: anonymize other types of messages.
        }

        println()
        println("(W): photo added from the website.")
    }
}
