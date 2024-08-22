package com.example.iti_project.ui.RecipeActivity.About

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.models.Meals

import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepo
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment.LoginViewModel
import com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment.LoginViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}

