package com.github.artemo24.dyrbok.backupandrestore.utilities

import com.google.cloud.Timestamp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Simple serializer for the Firestore Timestamp type. Only the seconds property is used; the nanoseconds property is
 * set to zero.
 */
object TimestampSerializer : KSerializer<Timestamp> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Timestamp", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Timestamp) =
        encoder.encodeLong(value.seconds)

    override fun deserialize(decoder: Decoder): Timestamp =
        Timestamp.ofTimeSecondsAndNanos(decoder.decodeLong(), 0)
}
