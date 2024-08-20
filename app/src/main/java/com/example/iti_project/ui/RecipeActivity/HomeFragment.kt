package com.example.iti_project.ui.RecipeActivity

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
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment.LoginViewModel
import com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment.LoginViewModelFactory

class HomeFragment : Fragment() {

    val loginViewModel: LoginViewModel by viewModels(){
        LoginViewModelFactory(UserRepoImp(
            LocalDataSourceImp(
                RoomDataBaseImp.getInstance(requireContext()),
            SharedPreferenceImp.getInstance(requireContext())
        )
        ))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val s = loginViewModel.addFavorite()
//            Log.i("favoriteIDs", ""+ i.toString())

    }
}