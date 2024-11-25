// See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serializers.md#serializing-3rd-party-classes
@file:UseSerializers(TimestampSerializer::class)


package com.github.artemo24.dyrbok.backupandrestore.dataclasses

import com.github.artemo24.dyrbok.backupandrestore.utilities.TimestampSerializer
import com.google.cloud.Timestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers


@Serializable
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class Logging : FirestoreObject {
    var logging_id: String = ""
    var date_time: Timestamp = Timestamp.MIN_VALUE
    var logging_level: String = ""
    var message: String = ""
    var tag: String = ""
    var user_id: String = ""

    override fun toString(): String =
        "Logging with ID '$logging_id'"

    override fun compareTo(other: FirestoreObject): Int =
        if (other is Logging) date_time.compareTo(other.date_time) else 0
}
