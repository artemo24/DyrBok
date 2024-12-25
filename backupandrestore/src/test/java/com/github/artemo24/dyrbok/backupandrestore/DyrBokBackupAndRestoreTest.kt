package com.github.artemo24.dyrbok.backupandrestore

import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreTestMock
import com.github.artemo24.dyrbok.backupandrestore.main.DyrBokBackupAndRestore
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import kotlin.io.path.absolutePathString


class DyrBokBackupAndRestoreTest {
    private lateinit var firestore: FirestoreTestMock
    private lateinit var backupAndRestore: DyrBokBackupAndRestore

    @Before
    fun setUp() {
        firestore = FirestoreTestMock()
        backupAndRestore = DyrBokBackupAndRestore()
    }

    @Test
    fun test_readFirestoreDocumentsToObjects_success() {
        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)

        assertEquals(firestore.getTestDataByCollectionName("logging"), firestoreObjects.logging)
    }

    @Test
    fun test_writeObjectsToJsonFiles_success() {
        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)
        var temporaryDirectoryFilepath: String? = null

        try {
            temporaryDirectoryFilepath = createTemporaryDirectory()

            backupAndRestore.writeObjectsToJsonFiles(firestoreObjects, outputDirectory = temporaryDirectoryFilepath)
            val readFirestoreObjects = backupAndRestore.readObjectsFromJsonFiles(inputDirectory = temporaryDirectoryFilepath)

            assertEquals(firestoreObjects, readFirestoreObjects)

        } finally {
            deleteTemporaryDirectory(temporaryDirectoryFilepath)
        }
    }

    @Test
    fun test_zipJsonFiles_success() {
        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)
        var temporaryDirectoryFilepath1: String? = null
        var temporaryDirectoryFilepath2: String? = null

        try {
            temporaryDirectoryFilepath1 = createTemporaryDirectory(suffix = "1")
            temporaryDirectoryFilepath2 = createTemporaryDirectory(suffix = "2")

            backupAndRestore.writeObjectsToJsonFiles(firestoreObjects, outputDirectory = temporaryDirectoryFilepath1)
            val backupZipFileAbsolutePath = backupAndRestore.zipJsonFiles(outputDirectory = temporaryDirectoryFilepath1)
            backupAndRestore.unzipJsonFiles(backupZipFileAbsolutePath, outputDirectory = temporaryDirectoryFilepath2)
            val readFirestoreObjects = backupAndRestore.readObjectsFromJsonFiles(inputDirectory = temporaryDirectoryFilepath2)

            assertEquals(firestoreObjects, readFirestoreObjects)
        } finally {
            deleteTemporaryDirectory(temporaryDirectoryFilepath1)
            deleteTemporaryDirectory(temporaryDirectoryFilepath2)
        }
    }

    private fun createTemporaryDirectory(suffix: String = ""): String {
        val appendText = if (suffix.isNotEmpty()) "$suffix--" else ""
        val directoryName = "temporary-directory--unit-tests--$appendText"
        val temporaryDirectory = Files.createTempDirectory(directoryName)

        return temporaryDirectory.absolutePathString()
    }

    private fun deleteTemporaryDirectory(temporaryDirectoryFilepath: String?) {
        if (temporaryDirectoryFilepath != null) {
            File(temporaryDirectoryFilepath).deleteRecursively()
        }
    }
}
