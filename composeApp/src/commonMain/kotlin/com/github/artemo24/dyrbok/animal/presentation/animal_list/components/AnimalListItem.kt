package com.github.artemo24.dyrbok.animal.presentation.animal_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.core.presentation.LightBlue


@Composable
fun AnimalListItem(
    animal: Animal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable(onClick = onClick),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Text(text = animal.name)
    }
}
