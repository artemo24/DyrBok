package com.github.artemo24.dyrbok.websitescraper.htmlhandlers

//import android.util.Log
import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus
import com.github.artemo24.dyrbok.utilities.logging.Log
import com.github.artemo24.dyrbok.websitescraper.dataclasses.WebsiteAnimalOverviewItem
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler


class AnimalsOverviewHtmlHandler : KsoupHtmlHandler {
    private val logTag = AnimalsOverviewHtmlHandler::class.simpleName

    private var divLevelCount = 0
    private var divLevelAnimal = 0
    private var inAdoptionStatusSpan = false
    private var adoptionStatus = AdoptionStatus.AVAILABLE
    private var inAnimalH3 = false
    private var animalName = ""
    private val animalNamesToAnimalOverviewItems = mutableMapOf<String, WebsiteAnimalOverviewItem>()

    override fun onOpenTag(name: String, attributes: Map<String, String>, isImplied: Boolean) {
        when (name) {
            "div" -> {
                divLevelCount++

                when (attributes["class"]) {
                    "col-12 col-md-4 mb-2 mb-md-1" -> {
                        addAnimalOverviewItem("")

                        divLevelAnimal = divLevelCount
                    }
                }
            }

            "span" -> {
                if (attributes["class"] in listOf("btn btn-primary", "btn btn-light")) {
                    inAdoptionStatusSpan = true
                }
            }

            "h3" -> {
                if (divLevelAnimal > 0) {
                    inAnimalH3 = true
                }
            }

            "a" -> {
                addAnimalOverviewItem(attributes["href"])
            }
        }
    }

    private fun addAnimalOverviewItem(webpageUrl: String?) {
        if (animalName.isNotBlank() && webpageUrl != null) {
            animalNamesToAnimalOverviewItems[animalName] = WebsiteAnimalOverviewItem(animalName, webpageUrl, adoptionStatus)
        }

        adoptionStatus = AdoptionStatus.AVAILABLE
        animalName = ""
    }

    override fun onText(text: String) {
        if (inAdoptionStatusSpan) {
            if (text == AdoptionStatus.RESERVED.websiteName) {
                adoptionStatus = AdoptionStatus.RESERVED
            } else if (text == AdoptionStatus.ADOPTED.websiteName) {
                adoptionStatus = AdoptionStatus.ADOPTED
            }
        } else if (inAnimalH3) {
            if (animalName.isNotBlank()) {
                Log.d(logTag, "Appending text '$text' to animal name '$animalName'.")
            }

            animalName += text
        }
    }

    override fun onCloseTag(name: String, isImplied: Boolean) {
        when (name) {
            "div" -> {
                if (divLevelCount <= divLevelAnimal) {
                    divLevelAnimal = 0
                }

                divLevelCount--
            }

            "span" -> {
                inAdoptionStatusSpan = false
            }

            "h3" -> {
                inAnimalH3 = false
            }
        }
    }

    fun getAnimalNamesToAnimalOverviewItems(): Map<String, WebsiteAnimalOverviewItem> {
        return animalNamesToAnimalOverviewItems
    }
}
