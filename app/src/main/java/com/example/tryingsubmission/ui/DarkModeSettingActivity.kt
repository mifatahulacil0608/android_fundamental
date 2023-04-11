package com.example.tryingsubmission.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.tryingsubmission.ui.viewmodel.DarkModeViewModel
import com.example.tryingsubmission.R
import com.example.tryingsubmission.ui.factoryviewmodel.SettingPreferences
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactorySetting

import com.google.android.material.switchmaterial.SwitchMaterial



val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")
class DarkModeSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode_setting)

        val preferences = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactorySetting(preferences))[DarkModeViewModel::class.java]

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        mainViewModel.getThemeSetting().observe(this){isChecked : Boolean->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _:CompoundButton?, isChecked : Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}