package com.huy.chess.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.huy.chess.proto.User
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException

object UserSerializer : Serializer<User> {
    override val defaultValue: User = User.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): User {
        try {
            return User.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: User,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.userDataStore: DataStore<User> by dataStore(
    fileName = "user.pb",
    serializer = UserSerializer
)