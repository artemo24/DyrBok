// See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serializers.md#serializing-3rd-party-classes
@file:UseSerializers(TimestampSerializer::class)


package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import com.github.artemo24.dyrbok.backupandrestore.utilities.TimestampSerializer
import com.google.cloud.Timestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers


@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class Settings : FirestoreObject {
    var setting_id: String = ""
    var home_screen_messages: String = ""
    var log_quiz_usage: String = ""
    var log_screen_switches: String = ""
    var new_users_administrator_email_address: String = ""
    var refresh_pets_days: Int = -1
    var show_home_screen_buttons: String = ""
    var show_home_screen_message: String = ""
    var show_mobile_data_usage: Boolean = false
    var updated_pets_bird_date_time: Timestamp = Timestamp.MIN_VALUE
    var updated_pets_cat_date_time: Timestamp = Timestamp.MIN_VALUE
    var updated_pets_dog_date_time: Timestamp = Timestamp.MIN_VALUE
    var updated_pets_rabbit_date_time: Timestamp = Timestamp.MIN_VALUE
    var updated_pets_rodent_date_time: Timestamp = Timestamp.MIN_VALUE

    override fun toString(): String =
        "Settings with ID '$setting_id'"

    override fun compareTo(other: FirestoreObject): Int =
        if (other is Settings) setting_id.compareTo(other.setting_id) else 0
}
