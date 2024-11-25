package com.github.artemo24.dyrbok.backupandrestore

import com.github.artemo24.dyrbok.backupandrestore.firestore.FirestoreTestMock
import org.junit.Assert.assertEquals
import org.junit.Test


class DyrBokBackupAndRestoreTest {
    @Test
    fun testReadFirestoreDocumentsToObjects_success() {
        val firestore = FirestoreTestMock()
        val backupAndRestore = DyrBokBackupAndRestore()

        val firestoreObjects = backupAndRestore.readFirestoreDocumentsToObjects(firestore)

        assertEquals(firestore.getTestDataByCollectionName("logging"), firestoreObjects.logging)
    }
}
