package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.Language
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object LanguageSerializer : Serializer<Language> {
    override val defaultValue: Language = Language.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Language {
        try {
            return Language.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Language,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.languageDataStore: DataStore<Language> by dataStore(
    fileName = "language.pb",
    serializer = LanguageSerializer
)