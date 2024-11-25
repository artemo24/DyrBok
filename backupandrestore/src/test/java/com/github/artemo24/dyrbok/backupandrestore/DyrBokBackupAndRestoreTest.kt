package com.github.artemo24.dyrbok.backupandrestore

import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreTestMock
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.nio.file.Files


class DyrBokBackupAndRestoreTest {
    @Test
    fun testReadFirestoreDocumentsToObjects_success() {
        val firestore = FirestoreTestMock()
        val backupAndRestore = DyrBokBackupAndRestore()

        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)

        assertEquals(firestore.getTestDataByCollectionName("logging"), firestoreObjects.logging)
    }

    @Test
    fun writeObjectsToJsonFiles_success() {
        val firestore = FirestoreTestMock()
        val backupAndRestore = DyrBokBackupAndRestore()
        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)

        var temporaryDirectoryFilepath: String? = null

        try {
            val temporaryDirectory = Files.createTempDirectory("temporary-directory-unit-tests")
            temporaryDirectoryFilepath = temporaryDirectory.toFile().absolutePath

            backupAndRestore.writeObjectsToJsonFiles(firestoreObjects, outputDirectory = temporaryDirectoryFilepath)

            val readFirestoreObjects = backupAndRestore.readObjectsFromJsonFiles(inputDirectory = temporaryDirectoryFilepath)
            assertEquals(firestoreObjects, readFirestoreObjects)

        } finally {
            if (temporaryDirectoryFilepath != null) {
                File(temporaryDirectoryFilepath).deleteRecursively()
            }
        }
    }
}
