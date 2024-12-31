package com.github.artemo24.dyrbok.backupandrestore.utilities

import com.github.artemo24.dyrbok.backupandrestore.dataclasses.MediaItem


class StorageUtilities {
    companion object {
        private const val PET_PHOTOS_DIRECTORY = "pet_photos"

        private var photoBucketUrl = ""

        fun getPhotoBucketUrl(): String = photoBucketUrl

        fun setPhotoBucketUrl(newBucketUrl: String) {
            photoBucketUrl = newBucketUrl
        }

        fun getPhotoUrl(photoFilename: String): String {
            val photoPath = "$PET_PHOTOS_DIRECTORY%2F$photoFilename"
            val url = "${photoBucketUrl}o/$photoPath?alt=media"

            return url.replace(" ", "%20")
        }

        fun getShortMediaItemUrl(mediaItem: MediaItem): String {
            val websitePrefix = "https://www.dierenasielleiden.nl/wp-content/uploads/"
            val firebasePrefix = getPhotoBucketUrl()
            val gitHubPrefix = "https://raw.githubusercontent.com/FreekDB/repository-size-test/main/"

            val photoUrl = mediaItem.photoUrl()

            return if (photoUrl != null) {
                when {
                    photoUrl.startsWith(prefix = websitePrefix) -> "website: ${photoUrl.substringAfter(delimiter = websitePrefix)}"
                    photoUrl.startsWith(prefix = firebasePrefix) -> "Firebase: ${mediaItem.storage_filepath.substringAfterLast(delimiter = "/")}"
                    photoUrl.startsWith(prefix = gitHubPrefix) -> "GitHub: ${photoUrl.substringAfter(delimiter = gitHubPrefix)}"
                    else -> photoUrl
                }
            } else {
                "null"
            }
        }
    }
}
