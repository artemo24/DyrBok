package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import kotlinx.serialization.Serializable


/**
 * This class can be used to create ScreenSwitch Kotlin objects from Firestore documents.
 */
@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class ScreenSwitch : FirestoreObject {
    var screen_switch_id: String = ""
    val date_time: String = ""
    val duration_milliseconds: Long = -1
    val email: String = ""
    val firebase_user_id: String = ""
    val parameters: String = ""
    val source_screen_name: String = ""
    val target_screen_name: String = ""

    override fun toString(): String {
        val parametersPart = if (parameters.isEmpty()) "" else " with parameters '$parameters'"

        return "Screen switch on ${date_time.take(19)} by $firebase_user_id from '$source_screen_name' to '$target_screen_name'$parametersPart"
    }

    override fun compareTo(other: FirestoreObject): Int =
        if (other is ScreenSwitch) date_time.compareTo(other.date_time) else 0
}
