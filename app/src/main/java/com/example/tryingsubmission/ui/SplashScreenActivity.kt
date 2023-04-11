package com.example.tryingsubmission.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tryingsubmission.ui.viewmodel.DarkModeViewModel
import com.example.tryingsubmission.R
import com.example.tryingsubmission.databinding.ActivitySplashScreenBinding
import com.example.tryingsubmission.ui.factoryviewmodel.SettingPreferences
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactorySetting

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    private lateinit var imageSplashScreen : ImageView
    private lateinit var splashLayout : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactorySetting(pref))[DarkModeViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this){
            isDarkModeActive : Boolean ->
            setThemeSplashScreen(isDarkModeActive)
        }


        splashLayout = binding.splashLayout
        imageSplashScreen = binding.splashScreen

        imageSplashScreen.alpha = 0f

        imageSplashScreen.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
    private fun setThemeSplashScreen(isDark : Boolean){
        val drawable = if (isDark) R.drawable.github_logo_white else R.drawable.github_logo_black
        imageSplashScreen.setImageDrawable(ContextCompat.getDrawable(imageSplashScreen.context, drawable))

        val color = if (isDark) R.color.black else R.color.white
        splashLayout.setBackgroundColor(ContextCompat.getColor(splashLayout.context, color))

    }
}