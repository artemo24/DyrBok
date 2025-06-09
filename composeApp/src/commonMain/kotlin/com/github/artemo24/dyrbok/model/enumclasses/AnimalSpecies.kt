package com.github.artemo24.dyrbok.model.enumclasses


@Suppress("SpellCheckingInspection")
enum class AnimalSpecies(val websiteSpeciesName: String) {
    BIRD(websiteSpeciesName = "vogel"),
    CAT(websiteSpeciesName = "kat"),
    DOG(websiteSpeciesName = "hond"),
    RABBIT(websiteSpeciesName = "konijn"),
    RODENT(websiteSpeciesName = "knaagdier"),
    UNKNOWN_ANIMAL_SPECIES(websiteSpeciesName = "");

    fun capitalizedName(): String = "${name.take(1)}${name.drop(1).lowercase()}"
    fun databaseName(): String = name.lowercase()

    companion object {
        private val drawableResourceIdMap = mutableMapOf<AnimalSpecies, Int>()
        private val pluralsResourceIdMap = mutableMapOf<AnimalSpecies, Int>()

        fun initializeDrawableResourceIdMap(map: Map<AnimalSpecies, Int>) =
            drawableResourceIdMap.putAll(map)

        fun initializePluralsResourceIdMap(map: Map<AnimalSpecies, Int>) =
            pluralsResourceIdMap.putAll(map)

        fun getDrawableResourceId(animalSpecies: AnimalSpecies): Int =
            drawableResourceIdMap[animalSpecies] ?: -1

        fun getPluralsResourceId(animalSpecies: AnimalSpecies): Int =
            drawableResourceIdMap[animalSpecies] ?: -1

        fun databaseNameToAnimalSpecies(databaseName: String): AnimalSpecies {
            return try {
                valueOf(databaseName.uppercase())
            } catch (e: IllegalArgumentException) {
                UNKNOWN_ANIMAL_SPECIES
            }
        }
    }
}
