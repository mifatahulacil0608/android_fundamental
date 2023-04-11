package com.example.tryingsubmission.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryingsubmission.data.remote.response.GithubResponse
import com.example.tryingsubmission.ui.viewmodel.DarkModeViewModel

import com.example.tryingsubmission.R
import com.example.tryingsubmission.data.adapter.ListUserAdapter
import com.example.tryingsubmission.databinding.ActivityMainBinding
import com.example.tryingsubmission.data.remote.Result
import com.example.tryingsubmission.ui.factoryviewmodel.SettingPreferences
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactory
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactorySetting
import com.example.tryingsubmission.ui.viewmodel.MainViewModel

@Suppress("DEPRECATION", "COMPATIBILITY_WARNING", "UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecyclerList()


        val preferences = SettingPreferences.getInstance(dataStore)
        val mainViewModelDarkMode = ViewModelProvider(
            this,
            ViewModelFactorySetting(preferences)
        )[DarkModeViewModel::class.java]

        mainViewModelDarkMode.getThemeSetting().observe(this){isChecked : Boolean->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        mainViewModel = viewModels<MainViewModel> {
            ViewModelFactory.getInstance(application)
        }.value


        mainViewModel.githubUser.observe(this){
            observeResult(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        fun searchUser(username: String) {
            mainViewModel.getSearchDataUser(username)
            mainViewModel.githubUser.observe(this@MainActivity) {
                observeResult(it)
            }
        }

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchUser(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settings ->{
                val intent = Intent(this,DarkModeSettingActivity::class.java)
                startActivity(intent)
                true
            }R.id.favorite ->{
                val intent = Intent(this,FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true

        }

    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.listUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUser.addItemDecoration(itemDecoration)
    }
    private fun observeResult(result: Result<*>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                val data = result.data
                if (data is GithubResponse) {
                    setData(data)
                }
            }
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    private fun setData(response: GithubResponse) {
        val adapter = ListUserAdapter(response.items)
        binding.listUser.adapter = adapter
    }
    fun getBinding(): ActivityMainBinding {
        return binding
    }

}
