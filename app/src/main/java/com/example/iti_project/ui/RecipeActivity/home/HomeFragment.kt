package com.example.iti_project.ui.RecipeActivity.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.google.android.material.search.SearchBar


class HomeFragment : Fragment() {

    private lateinit var searchBar: SearchBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog: ProgressDialog
    private val viewModel: HomeFragmentViewModel by viewModels() {
        ProductViewModelFactory(
            MealsRepoImpl(), FavoriteRecipeRepoImp(
                LocalDataSourceImp(
                    requireContext(),
                    RoomDataBaseImp.getInstance(requireContext()),
                    SharedPreferenceImp.getInstance(requireContext())
                )
            )
        )
    }
    private lateinit var adapter: AdapterForListRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMeals()
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
        searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.list_recipe)
        mProgressDialog = ProgressDialog(requireContext())
        adapter = AdapterForListRecipe{id , count ->
            val action = HomeFragmentDirections.actionHomeToRecipeDetailsFragment(count, id)
            findNavController().navigate(action)
        }

        searchBar.setOnClickListener {
            findNavController().navigate(R.id.search)
        }


        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)



        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            when (meals) {
                is UiState.Error -> {
                    mProgressDialog.cancel()
                    Toast.makeText(
                        requireContext(),
                        "there is an error :${meals.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("error", meals.errorMessage)
                }

                is UiState.Loading -> {
                    mProgressDialog.setTitle("Loading Data")
                    mProgressDialog.setMessage("please wait while we are loading data")
                    mProgressDialog.show()
                    Toast.makeText(requireContext(), "loading ", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    adapter.setData(meals.data)
//                    viewModel.favoriteUserIds.observe(viewLifecycleOwner){
//
//
////                        delay(2000)
//                    }

                    mProgressDialog.cancel()
                }

            }
        }
        listenToUpdateFavouriteItems()
        listenToDeleteFavouriteItems()
        listenToAddFavouriteItems()

    }

    private fun listenToDeleteFavouriteItems() {
        adapter.favoriteUserRemovedIds.observe(viewLifecycleOwner) { response ->
            if (response != null ) {
                viewModel.deleteFavoriteRecipe(response)
            }
        }
    }

    private fun listenToAddFavouriteItems() {
        adapter.favoriteUserAddMeal.observe(viewLifecycleOwner) { response ->
            if (response != null ) {
                viewModel.addFavoriteRecipe(response)

            }
        }
    }
    private fun listenToUpdateFavouriteItems() {
        viewModel.favoriteUserIds.observe(viewLifecycleOwner) { response ->
            if (!response.isNullOrEmpty() ) {
                adapter.updateIDs(response as MutableList<String>)
//                favoriteRecipesAdapter.setData(viewModel.favoriteRecipes , viewModel.favoriteUserIds)
            }
        }
    }

}