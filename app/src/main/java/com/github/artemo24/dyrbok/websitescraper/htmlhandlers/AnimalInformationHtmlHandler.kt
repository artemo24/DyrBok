package com.github.artemo24.dyrbok.websitescraper.htmlhandlers

import com.github.artemo24.dyrbok.websitescraper.dataclasses.WebsiteAnimalInformation
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import java.net.URI


class AnimalInformationHtmlHandler : KsoupHtmlHandler {
    // Log: private val logTag = PetInformationHtmlHandler::class.simpleName

    private var websiteAnimalInformation = WebsiteAnimalInformation("", emptyList())
    private var inDivCol12 = false
    private var inParagraphCol12 = false

    override fun onOpenTag(name: String, attributes: Map<String, String>, isImplied: Boolean) {
        when (name) {
            "a" -> {
                if (attributes["class"] == "glightbox") {
                    val href = attributes["href"]
                    if (!href.isNullOrBlank()) {
                        val photoUrls = websiteAnimalInformation.mediaItemUrls.toMutableList()
                        photoUrls.add(URI(href.replace("-scaled", "")).toURL())
                        websiteAnimalInformation = websiteAnimalInformation.copy(mediaItemUrls = photoUrls.toList())
                    }
                }
            }

            "div" -> {
                if (attributes["class"] == "col-12") {
                    inDivCol12 = true
                }
            }

            "p" -> {
                if (inDivCol12) {
                    inParagraphCol12 = true
                }
            }
        }
    }

    override fun onText(text: String) {
        if (inParagraphCol12) {
            val currentDescription = websiteAnimalInformation.description + if (websiteAnimalInformation.description.isNotEmpty()) "\n" else ""
            websiteAnimalInformation = websiteAnimalInformation.copy(description = currentDescription + text.trim())
        }
    }

    override fun onCloseTag(name: String, isImplied: Boolean) {
        when (name) {
            "div" -> {
                inDivCol12 = false
            }

            "p" -> {
                inParagraphCol12 = false
            }
        }
    }

    fun getAnimalInformation(): WebsiteAnimalInformation {
        return websiteAnimalInformation
    }
}
