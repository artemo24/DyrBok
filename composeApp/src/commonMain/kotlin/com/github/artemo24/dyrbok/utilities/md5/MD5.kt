package com.github.artemo24.dyrbok.utilities.md5

import kotlin.math.abs
import kotlin.math.sin


object MD5 {
    private val rotateAmounts = listOf(
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21,
    )

    private val md5Constants = (0..63).map { (abs(sin(it + 1.0)) * (1L shl 32)).toUInt() }

    private val md5Functions = listOf(
        { b: UInt, c: UInt, d: UInt -> (b and c) or (b.inv() and d) },
        { b: UInt, c: UInt, d: UInt -> (b and d) or (c and d.inv()) },
        { b: UInt, c: UInt, d: UInt -> b xor c xor d },
        { b: UInt, c: UInt, d: UInt -> c xor (b or d.inv()) },
    )

    private val indexFunctions = listOf(
        { wordIndex: Int -> wordIndex },
        { wordIndex: Int -> (wordIndex * 5 + 1) and 0x0f },
        { wordIndex: Int -> (wordIndex * 3 + 5) and 0x0f },
        { wordIndex: Int -> (wordIndex * 7) and 0x0f },
    )

    // Compute the MD5 hash of the given message.
    fun computeMD5Hash(message: ByteArray): ByteArray {
        var a0 = 0x67452301.toUInt()
        var b0 = 0xefcdab89.toUInt()
        var c0 = 0x98badcfe.toUInt()
        var d0 = 0x10325476.toUInt()

        val messageList = message.map { it.toUByte() }.toMutableList()
        messageList.addAll(getPaddingBytes(message.size))

        for (chunkOffset in 0..<messageList.size step 64) {
            val chunkWords = getChunkWords(messageList, chunkOffset)

            var a = a0
            var b = b0
            var c = c0
            var d = d0

            for (wordIndex in 0..63) {
                val f = md5Functions[wordIndex ushr 4](b, c, d)
                val g = indexFunctions[wordIndex ushr 4](wordIndex)

                val newB = b + (f + a + md5Constants[wordIndex] + chunkWords[g]).rotateLeft(rotateAmounts[wordIndex])

                a = d
                d = c
                c = b
                b = newB
            }

            a0 += a
            b0 += b
            c0 += c
            d0 += d
        }

        return listOf(a0, b0, c0, d0).map { bytesFromInt(it) }.flatten().map { it.toByte() }.toByteArray()
    }

    // Get the padding bytes for the message: a byte with the most significant bit on, a number of zero value bytes and
    // 8 bytes with the message length in bits (modulo 2**64).
    private fun getPaddingBytes(messageLengthBytes: Int): List<UByte> {
        val paddingBytes = mutableListOf<UByte>()

        // Set the first bit to one.
        paddingBytes.add(0x80.toUByte())

        // Pad with zeros.
        while ((messageLengthBytes + paddingBytes.size) % 64 != 56) {
            paddingBytes.add(0x00.toUByte())
        }

        // Set the last 8 bytes to the message length in bits (modulo 2**64).
        var messageLengthBits = messageLengthBytes * 8
        for (byteIndex in 0..7) {
            paddingBytes.add(messageLengthBits.toUByte())
            messageLengthBits = messageLengthBits ushr 8
        }

        return paddingBytes
    }

    // Get a list with 16 words (4 bytes unsigned integers) from a chunk of the message.
    private fun getChunkWords(messageList: List<UByte>, chunkOffset: Int): List<UInt> =
        messageList
            .subList(chunkOffset, chunkOffset + 64)
            .chunked(4)
            .map(::intFromBytes)

    // Convert a 4 bytes unsigned integer to a list of 4 unsigned bytes (little-endian byte order).
    private fun bytesFromInt(number: UInt): List<UByte> =
        listOf(number.toUByte(), (number shr 8).toUByte(), (number shr 16).toUByte(), (number shr 24).toUByte())

    // Convert a list of 4 unsigned bytes (little-endian byte order) to 4 bytes unsigned integer.
    private fun intFromBytes(bytes: List<UByte>): UInt {
        return bytes[0].toUInt() or (bytes[1].toUInt() shl 8) or (bytes[2].toUInt() shl 16) or (bytes[3].toUInt() shl 24)
    }
}
