package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import kotlinx.serialization.Serializable


@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class Pet : FirestoreObject {
    var pet_id: String = ""
    var adoption_status: String = ""
    var description: String = ""
    var name: String = ""
    var pet_type: String = ""
    var photos_wanted: Boolean = false
    var unique_name: String = ""
    var visible: Boolean = false
    var webpage_url: String = ""

    override fun toString(): String =
        "Pet $name (adoption status: ${adoption_status.lowercase()})"

    override fun compareTo(other: FirestoreObject): Int =
        if (other is Pet) name.compareTo(other.name) else 0
}
