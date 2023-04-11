package com.example.tryingsubmission.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.R
import com.example.tryingsubmission.data.adapter.ListUserAdapter
import com.example.tryingsubmission.databinding.FragmentFollowGithubUserBinding
import com.example.tryingsubmission.data.remote.Result
import com.example.tryingsubmission.ui.factoryviewmodel.ViewModelFactory
import com.example.tryingsubmission.ui.viewmodel.FollowViewModel

class FollowFragmentGithubUser : Fragment(R.layout.fragment_follow_github_user) {
    private var _binding:FragmentFollowGithubUserBinding?=null
    private val binding get() = _binding!!
    private lateinit var listFollowViewModel: FollowViewModel
    private lateinit var username: String
    private var position: Int = 0


    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowGithubUserBinding.bind(view)
        layoutManager()
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER,0)
            username= it.getString(ARG_NAME,"Empty")
        }

        listFollowViewModel = viewModels<FollowViewModel> {
            ViewModelFactory.getInstance(requireActivity())
        }.value




        if (position == 1 ){
            listFollowViewModel.getFollowersGithubUser(username)
            listFollowViewModel.followersGithubUser.observe(viewLifecycleOwner){
                observable(it)
            }
        }else{
            listFollowViewModel.getFollowingGithubUser(username)
            listFollowViewModel.followingGithubUser.observe(viewLifecycleOwner){
                observable(it)
            }

        }


    }


    private fun layoutManager(){
        binding.listFollow.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.listFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(),layoutManager.orientation)
        binding.listFollow.addItemDecoration(itemDecoration)
    }

    private fun observable (result : Result<List<ItemsItem>>){
        when(result){
            is Result.Loading ->{
                binding.progressBar2.visibility = View.VISIBLE
            }
            is Result.Success ->{
                binding.progressBar2.visibility = View.GONE
                val data = result.data
                setData(data)
            }
            is Result.Error ->{
                binding.progressBar2.visibility = View.GONE
            }
        }
    }

    private fun setData(item: List<ItemsItem>){
        val adapter = ListUserAdapter(item)
        binding.listFollow.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}