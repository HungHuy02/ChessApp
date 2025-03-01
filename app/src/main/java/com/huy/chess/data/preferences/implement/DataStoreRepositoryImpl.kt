package com.huy.chess.data.preferences.implement

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.huy.chess.data.preferences.abstraction.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "my_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    private suspend fun <T> putValue(preferencesKey: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences -> preferences[preferencesKey] = value }
    }

    private suspend fun <T> getValue(preferences: Preferences.Key<T>): T? {
        return try {
            context.dataStore.data.first()[preferences]
        } catch (e: Exception) {
            null
        }

    }

    override suspend fun putString(key: String, value: String) =
        putValue(stringPreferencesKey(key), value)

    override suspend fun putInt(key: String, value: Int) =
        putValue(intPreferencesKey(key), value)

    override suspend fun getString(key: String): String? =
        getValue(stringPreferencesKey(key))

    override suspend fun getInt(key: String): Int? =
        getValue(intPreferencesKey(key))
}