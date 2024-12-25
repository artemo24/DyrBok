package com.github.artemo24.dyrbok.backupandrestore.utilities


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
    }
}
