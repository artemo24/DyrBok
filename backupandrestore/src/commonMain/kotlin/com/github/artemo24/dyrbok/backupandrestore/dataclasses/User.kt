package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import kotlinx.serialization.Serializable


@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class User : FirestoreObject {
    var user_id: String = ""
    var email_address: String = ""
    var firebase_user_id: String = ""
    var name: String = ""
    var roles: String = ""
    var user_status: String = ""

    override fun toString(): String =
        "User $name (status: $user_status)"

    override fun compareTo(other: FirestoreObject): Int =
        if (other is User) name.compareTo(other.name) else 0
}
