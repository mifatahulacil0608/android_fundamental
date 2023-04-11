package com.example.tryingsubmission.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tryingsubmission.data.remote.response.DetailUserResponse
import com.example.tryingsubmission.R
import com.example.tryingsubmission.data.adapter.SectionPagerAdapter
import com.example.tryingsubmission.data.local.entity.FavoriteEntity
import com.example.tryingsubmission.databinding.ActivityDetailUserBinding
import com.example.tryingsubmission.data.remote.Result
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactory
import com.example.tryingsubmission.ui.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailMainViewModel: DetailUserViewModel

    companion object{
        const val USERNAME_KEY = "usernameLogin"
        const val AVATARURL_KEY = "avatarUrl"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_one,
            R.string.tab_two
        )
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME_KEY)
        val avatarUrl = intent.getStringExtra(AVATARURL_KEY)

       val favorite = FavoriteEntity(username!!,avatarUrl)

        detailMainViewModel = viewModels<DetailUserViewModel> {
            ViewModelFactory.getInstance(application)
        }.value

        detailMainViewModel.getDetailGithubUser(username)
        detailMainViewModel.getDataFavorite(favorite)
        detailMainViewModel.githubUserDetail.observe(this){
            if (it !=null){
                when(it){
                    is Result.Loading ->{
                        binding.progressBar3.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding.progressBar3.visibility = View.GONE
                        setObservable(it.data)
                    }is Result.Error ->{
                    binding.progressBar3.visibility = View.GONE
                    }
                }
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        pager(sectionPagerAdapter, username)

        detailMainViewModel.favoriteUserByUsername.observe(this) {
            runOnUiThread {
                val login = it?.login
                if (login != null) {
                    setFavorite(true)
                } else {
                    setFavorite(false)
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            val favoriteBtn = FavoriteEntity(username,avatarUrl)
            val isFavorite = binding.btnFavorite.tag == "favorite"
            if (isFavorite){
                detailMainViewModel.deleteFavorite(favoriteBtn)
                Toast.makeText(this, "remove $username from favorite", Toast.LENGTH_SHORT)
                    .show()
            }else{
                detailMainViewModel.insertFavorite(favoriteBtn)
                Toast.makeText(this,"insert $username to favorite",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFavorite(isFavorite: Boolean){
        if (isFavorite){
            binding.btnFavorite.setText(R.string.remove_favorite)
            binding.btnFavorite.tag  = "favorite"
        }else{
            binding.btnFavorite.setText(R.string.add_favorite)
            binding.btnFavorite.tag = "unfavorite"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObservable(item : DetailUserResponse){
        binding.tvUsername.text = item.login
        binding.tvFullName.text = item.name
        binding.tvFollowers.text = "${item.followers} Followers"
        binding.tvFollowing.text = "${item.following} Following"
        Glide.with(this)
            .load(item.avatarUrl)
            .into(binding.tvImage)
    }
    private fun pager(sectionPagerAdapter: SectionPagerAdapter, username:String?){
        sectionPagerAdapter.username = username!!
        val viewPager:ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){tab,position->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()

        supportActionBar?.elevation = 0f
    }
}