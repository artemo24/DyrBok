package com.github.artemo24.dyrbok.animal.data.repository

import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.animal.domain.AnimalRepository
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import com.github.artemo24.dyrbok.core.domain.DataError
import com.github.artemo24.dyrbok.core.domain.Result
import dyrbok.composeapp.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Serializable
data class AnimalTestData(
    @SerialName("pet_id")
    val animalId: String,
    @SerialName("adoption_status")
    val adoptionStatus: String,
    val name: String,
    @SerialName("unique_name")
    val uniqueName: String,
    val description: String,
    @SerialName("pet_type")
    val animalSpecies: String,
    @SerialName("photos_wanted")
    val photosWanted: Boolean,
    val visible: Boolean,
    @SerialName("webpage_url")
    val webpageUrl: String,
)

class DefaultAnimalRepository : AnimalRepository {
    private lateinit var animals: List<Animal>

    private val jsonParser = Json {
        ignoreUnknownKeys = true
    }

    init {
        runBlocking {
            animals = readAnimalsTestData()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun readAnimalsTestData(): List<Animal> {
        val animalsJson = Res.readBytes(path = "files/testdata/animals.json").decodeToString()

        return jsonParser
            .decodeFromString<List<AnimalTestData>>(animalsJson)
            .filter { it.visible }
            .map { Animal(it.animalId, it.name, AnimalSpecies.valueOf(it.animalSpecies.uppercase())) }
    }

    override suspend fun getAnimalsBySpecies(animalSpecies: AnimalSpecies): Result<List<Animal>, DataError.Remote> =
        Result.Success(data = animals.filter { it.animalSpecies == animalSpecies })
}
