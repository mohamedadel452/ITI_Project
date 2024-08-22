package com.example.iti_project.ui.RecipeActivity.Favourit

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.example.iti_project.ui.RecipeActivity.homeFragment.HomeFragmentViewModel
import com.example.iti_project.ui.RecipeActivity.homeFragment.ProductViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {

    private lateinit var rv_favoriteRecipes : RecyclerView


    private lateinit var favoriteRecipesAdapter: FavoriteRecipesAdapter

    private val viewModel: FavoriteFragmentViewModel by viewModels(){
        FavoriteFragmentViewModelFactory(FavoriteRecipeRepoImp(LocalDataSourceImp( requireContext(), RoomDataBaseImp.getInstance(requireContext()) , SharedPreferenceImp.getInstance(requireContext()))))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_favoriteRecipes = view.findViewById(R.id.rv_favorite_recipe)

    }
    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel.getFavoriteList()
        return inflater.inflate(R.layout.fragment_favourit, container, false)
    }



    override fun onResume() {
        super.onResume()
        GlobalScope.launch (Dispatchers.Main){
            delay(2000)
            instantiateProductsRecyclerView()

            favoriteRecipesAdapter.setData(viewModel.favoriteRecipes , viewModel.favoriteUserIds)
            listenToDeleteFavouriteItems()
        }

    }
    private fun instantiateProductsRecyclerView() {
        favoriteRecipesAdapter = FavoriteRecipesAdapter{ favoriteRecipeID  ->
            val action =
                FavoriteFragmentDirections.actionFavouritToRecipeDetailsFragment(favoriteRecipeID)
            findNavController().navigate(action)
        }
        rv_favoriteRecipes.apply {
            layoutManager=
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoriteRecipesAdapter
        }
    }


    private fun listenToDeleteFavouriteItems() {
        favoriteRecipesAdapter.favoriteUserRemovedIds.observe(viewLifecycleOwner) { response ->
            if (!response.isNullOrEmpty() ) {
                viewModel.deleteFavoriteRecipe(response)
//                favoriteRecipesAdapter.setData(viewModel.favoriteRecipes , viewModel.favoriteUserIds)
            }
        }
    }

}