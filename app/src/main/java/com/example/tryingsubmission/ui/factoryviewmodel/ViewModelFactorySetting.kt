package com.example.tryingsubmission.ui.factoryviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tryingsubmission.ui.viewmodel.DarkModeViewModel

class ViewModelFactorySetting(private val preferences: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)){
            return DarkModeViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }


}