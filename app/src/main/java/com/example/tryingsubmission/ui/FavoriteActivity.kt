package com.example.tryingsubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.data.adapter.ListUserAdapter
import com.example.tryingsubmission.databinding.ActivityFavoriteBinding
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactory
import com.example.tryingsubmission.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecycleList()
        favoriteViewModel = viewModels<FavoriteViewModel> {
            ViewModelFactory.getInstance(application)
        }.value

        favoriteViewModel.getAllListFavorite().observe(this) { result ->
            val items = arrayListOf<ItemsItem>()
            result.map {
                val itemsItem = ItemsItem(login = it.login, avatarUrl = it.avatarUrl.toString())
                items.add(itemsItem)
            }
            setData(items)
        }

    }

    private fun showRecycleList(){
        binding.listFavorite.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(this)
        binding.listFavorite.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(this,layoutmanager.orientation)
        binding.listFavorite.addItemDecoration(itemDecoration)
    }
    private fun setData(user : List<ItemsItem>){
        val adapter = ListUserAdapter(user)
        binding.listFavorite.adapter = adapter
    }
}