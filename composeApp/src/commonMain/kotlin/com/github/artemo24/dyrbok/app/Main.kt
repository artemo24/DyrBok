package com.github.artemo24.dyrbok.app


class Main {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
