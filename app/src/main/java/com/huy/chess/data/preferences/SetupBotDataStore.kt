package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.SetupBotSettings
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object SetupBotSerializer : Serializer<SetupBotSettings> {
    override val defaultValue: SetupBotSettings = SetupBotSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SetupBotSettings {
        try {
            return SetupBotSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SetupBotSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.setupBotDataStore: DataStore<SetupBotSettings> by dataStore(
    fileName = "setupBot.pb",
    serializer = SetupBotSerializer
)