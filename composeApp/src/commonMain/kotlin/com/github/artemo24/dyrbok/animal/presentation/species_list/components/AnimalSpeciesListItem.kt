package com.github.artemo24.dyrbok.animal.presentation.species_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import com.github.artemo24.dyrbok.core.presentation.LightBlue
import dyrbok.composeapp.generated.resources.Res
import dyrbok.composeapp.generated.resources.birds
import dyrbok.composeapp.generated.resources.cats
import dyrbok.composeapp.generated.resources.dogs
import dyrbok.composeapp.generated.resources.rabbits
import dyrbok.composeapp.generated.resources.rodents
import org.jetbrains.compose.resources.stringResource


@Composable
fun AnimalSpeciesListItem(
    animalSpecies: AnimalSpecies,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable(onClick = onClick),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Text(text = getAnimalSpeciesName(animalSpecies))
    }
}

@Composable
private fun getAnimalSpeciesName(animalSpecies: AnimalSpecies): String =
    when (animalSpecies) {
        AnimalSpecies.BIRD -> stringResource(Res.string.birds)
        AnimalSpecies.CAT -> stringResource(Res.string.cats)
        AnimalSpecies.DOG -> stringResource(Res.string.dogs)
        AnimalSpecies.RABBIT -> stringResource(Res.string.rabbits)
        AnimalSpecies.RODENT -> stringResource(Res.string.rodents)
        AnimalSpecies.UNKNOWN -> "???"
    }
