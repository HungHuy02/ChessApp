package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.SetupTwoSettings
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object SetupTwoSerializer : Serializer<SetupTwoSettings> {
    override val defaultValue: SetupTwoSettings = SetupTwoSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SetupTwoSettings {
        try {
            return SetupTwoSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SetupTwoSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.setupTwoDataStore: DataStore<SetupTwoSettings> by dataStore(
    fileName = "setupTwo.pb",
    serializer = SetupTwoSerializer
)