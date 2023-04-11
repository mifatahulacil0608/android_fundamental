package com.example.tryingsubmission.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.tryingsubmission.databinding.ActivityMainBinding
import org.junit.Assert.*
import com.example.tryingsubmission.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    private lateinit var binding: ActivityMainBinding

    @Before
    fun setup() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            binding = it.getBinding()
        }
    }
    @Test
    fun testClickRecyclerViewItem(){
        val recyclerview = onView(withId(binding.listUser.id))
        recyclerview.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }


    @Test
    fun testClickFavoriteUserMenu(){
       onView(withId(R.id.favorite)).perform(click())
    }

    @Test
    fun testClickSettingMenu(){
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.settings)).perform(click())
    }
}