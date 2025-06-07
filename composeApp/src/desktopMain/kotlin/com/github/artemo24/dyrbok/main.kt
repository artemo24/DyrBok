package com.github.artemo24.dyrbok

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.artemo24.dyrbok.app.App
import com.github.artemo24.dyrbok.di.initKoin


fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DyrBok",
        ) {
            App()
        }
    }
}
