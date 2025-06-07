package com.github.artemo24.dyrbok

import androidx.compose.ui.window.ComposeUIViewController
import com.github.artemo24.dyrbok.app.App
import com.github.artemo24.dyrbok.di.initKoin


fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }
