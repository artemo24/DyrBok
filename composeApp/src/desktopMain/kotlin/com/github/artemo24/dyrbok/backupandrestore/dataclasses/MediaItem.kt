package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import com.github.artemo24.dyrbok.backupandrestore.utilities.StorageUtilities
import kotlinx.serialization.Serializable


/**
 * This class can be used to create MediaItem Kotlin objects from Firestore documents. Creating the objects is done by
 * the toObject method of the QueryDocumentSnapshot class (from the com.google.cloud.firestore package). I have not
 * found a way to use idiomatic property names.
 *
 * A cumbersome way to have idiomatic property names:
 *     private var pet_id: String = ""
 *     var petId: String
 *         get() = pet_id
 *         set(value) { pet_id = value }
 *
 * Creating a more custom version of the com.google.cloud.firestore.CustomClassMapper class would be another option.
 * Code: https://github.com/googleapis/java-firestore/blob/main/google-cloud-firestore/
 *           src/main/java/com/google/cloud/firestore/CustomClassMapper.java#L637
 *
 * For now, I will use non-idiomatic property names and suppress the warnings.
 *
 * In the future, I could generate (data) classes based on lists of properties.
 */
@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class MediaItem : FirestoreObject {
    var media_item_id: String = ""
    var capture_datetime: String = ""
    var pet_id: String = ""
    var pet_type: String = ""
    var photographer_user_id: String = ""
    var size: Long = -1
    var original_storage_filepath: String = ""  // todo: Remove (was used for a test with a photo on GitHub).
    var storage_filepath: String = ""
    var website_media_file_url: String = ""

    override fun toString(): String =
        "Media item with ID '$media_item_id' referencing a media item of the animal with ID '$pet_id'"

    override fun compareTo(other: FirestoreObject): Int =
        if (other is MediaItem) media_item_id.compareTo(other.media_item_id) else 0

    fun photoUrl(): String? =
        if (storage_filepath.isNotBlank()) {
            StorageUtilities.getPhotoUrl(storage_filepath.substringAfterLast(delimiter = "/"))
        } else if (website_media_file_url.isNotBlank()) {
            website_media_file_url
        } else {
            println("Media item $this does not have a valid photo URL.")

            null
        }
}
