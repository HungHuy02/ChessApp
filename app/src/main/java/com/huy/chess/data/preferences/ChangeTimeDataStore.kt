package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.ChangeTimeSettings
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object ChangeTimeSerializer : Serializer<ChangeTimeSettings> {
    override val defaultValue: ChangeTimeSettings = ChangeTimeSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ChangeTimeSettings {
        try {
            return ChangeTimeSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: ChangeTimeSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.changeTimeDataStore: DataStore<ChangeTimeSettings> by dataStore(
    fileName = "changeTime.pb",
    serializer = ChangeTimeSerializer
)