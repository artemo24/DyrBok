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

    override fun equals(other: Any?): Boolean =
        if (other is Logging) {
            logging_id == other.logging_id &&
                    date_time == other.date_time &&
                    logging_level == other.logging_level &&
                    message == other.message &&
                    tag == other.tag &&
                    user_id == other.user_id
        } else {
            false
        }

    override fun hashCode(): Int {
        var result = logging_id.hashCode()
        result = 31 * result + date_time.hashCode()
        result = 31 * result + logging_level.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + tag.hashCode()
        result = 31 * result + user_id.hashCode()
        return result
    }
}
