package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.Puzzle
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object PuzzleSerializer : Serializer<Puzzle> {
    override val defaultValue: Puzzle = Puzzle.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Puzzle {
        try {
            return Puzzle.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Puzzle,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.puzzleDataStore: DataStore<Puzzle> by dataStore(
    fileName = "puzzle.pb",
    serializer = PuzzleSerializer
)