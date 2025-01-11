package com.github.artemo24.dyrbok


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
