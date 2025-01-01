package com.github.artemo24.dyrbok.websitescraper

//import android.util.Log
import com.github.artemo24.dyrbok.utilities.logging.Log
import com.github.artemo24.dyrbok.websitescraper.dataclasses.WebsiteAnimalInformation
import com.github.artemo24.dyrbok.websitescraper.dataclasses.WebsiteAnimalOverviewItem
import com.github.artemo24.dyrbok.websitescraper.htmlhandlers.AnimalInformationHtmlHandler
import com.github.artemo24.dyrbok.websitescraper.htmlhandlers.AnimalsOverviewHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.URI
import java.util.stream.Collectors


class AnimalShelterWebsiteScraper(private val animalShelterWebsiteAddress: String, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private val logTag = AnimalShelterWebsiteScraper::class.simpleName

    // todo: Return a list instead of a map. This will allow multiple animals with the same name to be returned, which
    //       is an rare situation which can happen.
    suspend fun getAnimalNamesToAnimalOverviewItems(webAnimalSpeciesName: String): Map<String, WebsiteAnimalOverviewItem> =
        coroutineScope {
            async {
                Log.d(logTag, "getAnimalNamesToAnimalOverviewItems -- webAnimalSpeciesName: $webAnimalSpeciesName.")

                val listOfMaps = generateSequence(mutableListOf<Map<String, WebsiteAnimalOverviewItem>>()) { collectedNamesToOverviewItems ->
                    var animalNamesToAnimalOverviewItemsFromPage: Map<String, WebsiteAnimalOverviewItem>

                    runBlocking {
                        animalNamesToAnimalOverviewItemsFromPage = getAnimalNamesToAnimalOverviewItemsFromPage(
                            url = getAnimalsOverviewPageUrl(
                                webAnimalSpeciesName = webAnimalSpeciesName,
                                pageNumber = collectedNamesToOverviewItems.size + 1
                            )
                        )
                    }

                    collectedNamesToOverviewItems.add(animalNamesToAnimalOverviewItemsFromPage)

                    if (animalNamesToAnimalOverviewItemsFromPage.isNotEmpty()) collectedNamesToOverviewItems else null
                }.flatten()

                val animalNamesToOverviewItems = flattenListOfMaps(listOfMaps)

                Log.d(logTag, "getAnimalNamesToAnimalOverviewItems -- return animal map: $animalNamesToOverviewItems.")

                animalNamesToOverviewItems
            }.await()
        }

    suspend fun getAnimalInformation(webpageUrl: String): WebsiteAnimalInformation =
        coroutineScope { async { getAnimalInformationFromPage(webpageUrl) }.await() }

    private suspend fun getAnimalNamesToAnimalOverviewItemsFromPage(url: String): Map<String, WebsiteAnimalOverviewItem> {
        Log.d(logTag, "Get animal names from web page '$url'.")

        val animalsOverviewHtmlHandler = AnimalsOverviewHtmlHandler()
        parseHtml(url, animalsOverviewHtmlHandler)

        return animalsOverviewHtmlHandler.getAnimalNamesToAnimalOverviewItems()
    }

    private fun getAnimalsOverviewPageUrl(webAnimalSpeciesName: String, pageNumber: Int): String =
        "${animalShelterWebsiteAddress}plaatsbaar/?_soort=${webAnimalSpeciesName}&_paged=$pageNumber"

    private fun <K, V> flattenListOfMaps(listOfMaps: Sequence<Map<K, V>>): Map<K, V> =
        mutableMapOf<K, V>().apply { listOfMaps.forEach { map -> putAll(map) } }

    private suspend fun getAnimalInformationFromPage(url: String): WebsiteAnimalInformation {
        Log.d(logTag, "Get animal information from web page '$url'.")

        val animalInformationHtmlHandler = AnimalInformationHtmlHandler()
        parseHtml(url, animalInformationHtmlHandler)

        return animalInformationHtmlHandler.getAnimalInformation()
    }

    private suspend fun parseHtml(url: String, htmlHandler: KsoupHtmlHandler) {
        try {
            val ksoupHtmlParser = KsoupHtmlParser(htmlHandler)
            ksoupHtmlParser.write(readHtmlFromPage(url))
            ksoupHtmlParser.end()
        } catch (e: Exception) {
            Log.e(logTag, "Exception while retrieving and/or parsing html from url '$url'.", e)
        }
    }

    private suspend fun readHtmlFromPage(url: String): String {
        return withContext(dispatcher) {
            try {
                BufferedReader(InputStreamReader(URI(url).toURL().openStream()))
                    .lines()
                    .collect(Collectors.toList())
                    .joinToString(separator = " ")
            } catch (e: FileNotFoundException) {
                ""
            }
        }
    }
}
