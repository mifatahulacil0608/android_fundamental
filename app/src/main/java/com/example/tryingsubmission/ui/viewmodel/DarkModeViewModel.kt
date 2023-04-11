@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused")

package com.example.tryingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tryingsubmission.ui.factoryviewmodel.SettingPreferences

import kotlinx.coroutines.launch

@Suppress("unused", "unused", "unused")
class DarkModeViewModel(private val preferences: SettingPreferences) : ViewModel() {
    fun getThemeSetting() : LiveData<Boolean>{
        return preferences.getThemeSetting().asLiveData()
    }
    fun saveThemeSetting(isDarkModeActive : Boolean){
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}