package com.huy.chess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huy.chess.data.preferences.abstraction.DataStoreRepository
import com.huy.chess.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveButtonSelectedChangeTime(string: String) {
        viewModelScope.launch {
            repository.putString(Constants.DATASTORE_CHANGE_TIME_BUTTON_SELECTED, string)
        }
    }

    fun getButtonSelectedChangeTime() : String? = runBlocking {
        repository.getString(Constants.DATASTORE_CHANGE_TIME_BUTTON_SELECTED)
    }

    fun saveButtonSelectedSetupTwoPeople(string: String) {
        viewModelScope.launch {
            repository.putString(Constants.DATASTORE_TWO_PEOPLE_BUTTON_SELECTED, string)
        }
    }

    fun getButtonSelectedSetupTwoPeople() : String? = runBlocking {
        repository.getString(Constants.DATASTORE_TWO_PEOPLE_BUTTON_SELECTED)
    }

    fun saveButtonSelectedSetupBot(string: String) {
        viewModelScope.launch {
            repository.putString(Constants.DATASTORE_BOT_BUTTON_SELECTED, string)
        }
    }

    fun getButtonSelectedSetupBot() : String? = runBlocking {
        repository.getString(Constants.DATASTORE_BOT_BUTTON_SELECTED)
    }

}