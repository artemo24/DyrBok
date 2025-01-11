package com.github.artemo24.dyrbok

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DyrBok",
    ) {
        App()
    }
}
