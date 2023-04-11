package com.example.tryingsubmission.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tryingsubmission.ui.FollowFragmentGithubUser

class SectionPagerAdapter(detailUserActivity: AppCompatActivity) : FragmentStateAdapter(detailUserActivity) {
    var username =""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragmentGithubUser()

        fragment.arguments = Bundle().apply {
            putInt(FollowFragmentGithubUser.ARG_SECTION_NUMBER,position+1)
            putString(FollowFragmentGithubUser.ARG_NAME,username)
        }

        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}