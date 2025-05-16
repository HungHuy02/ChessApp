package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.DailyPuzzle
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object DailyPuzzleSerializer : Serializer<DailyPuzzle> {
    override val defaultValue: DailyPuzzle = DailyPuzzle.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DailyPuzzle {
        try {
            return DailyPuzzle.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: DailyPuzzle,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.dailyPuzzleDataStore: DataStore<DailyPuzzle> by dataStore(
    fileName = "dailyPuzzle.pb",
    serializer = DailyPuzzleSerializer
)