package com.github.artemo24.dyrbok.websitescraper

//import android.util.Log
import com.github.artemo24.dyrbok.utilities.logging.Log
import kotlinx.coroutines.runBlocking


fun main() {
    RunWebsiteScraper().runIt()
}


class RunWebsiteScraper {
    private val logTag = "RunWebsiteScraper"
    private val animalShelterWebsiteAddress = "https://www.dierenasielleiden.nl/"

    fun runIt() {
        runBlocking {
            val webAnimalSpeciesNames = listOf("hond", "kat", "konijn", "vogel", "knaagdier")
            val websiteScraper = AnimalShelterWebsiteScraper(animalShelterWebsiteAddress)

            webAnimalSpeciesNames.forEach { webAnimalSpeciesName ->
                val animalNamesToAnimalOverviewItemsMap = websiteScraper.getAnimalNamesToAnimalOverviewItems(webAnimalSpeciesName)

                Log.d(logTag, "=== === === === === === === === === === === === === === === === === === === === === === === === === === === ===")
                Log.d(logTag, "Animal names to animal overview items -- animal species $webAnimalSpeciesName:")

                animalNamesToAnimalOverviewItemsMap.keys
                    .sorted()
                    .forEach { animalName ->
                        val animalOverviewItem = animalNamesToAnimalOverviewItemsMap[animalName]

                        if (animalOverviewItem != null) {
                            val webpageUrl = animalOverviewItem.webpageUrl
                            val animalInformation = if (webpageUrl.isNotEmpty()) websiteScraper.getAnimalInformation(webpageUrl) else null

                            Log.d(logTag, "- $animalName:")
                            Log.d(logTag, "  + adoption status: ${animalOverviewItem.adoptionStatus}.")
                            Log.d(logTag, "  + web page URL: $webpageUrl.")

                            if (animalInformation != null) {
                                Log.d(logTag, "  + media item URLs:")
                                animalInformation.mediaItemUrls.forEach { mediaItemUrl -> Log.d(logTag, "    * $mediaItemUrl.") }
                                Log.d(logTag, "  + description: ${animalInformation.description}")
                            }
                        } else {
                            Log.e(logTag, "Animal overview item is null for animal name '$animalName'.", null)
                        }
                    }
            }
        }
    }
}
