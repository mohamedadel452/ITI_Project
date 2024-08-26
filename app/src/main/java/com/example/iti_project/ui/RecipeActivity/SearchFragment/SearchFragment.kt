package com.example.iti_project.ui.RecipeActivity.SearchFragment

import MealsAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.RemoteDataSource.RetrofitClient
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.example.iti_project.ui.RecipeActivity.home.HomeFragmentDirections
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class SearchFragment : Fragment() {
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var et_email: TextInputLayout

    private val searchViewModel: SearchViewModel by viewModels {

        SearchViewModel.SearchViewModelFactory(
            MealsRepoImpl(),
            FavoriteRecipeRepoImp(LocalDataSourceImp(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        // Initialize SearchBar and SearchView

        et_email = view.findViewById(R.id.sr4vw)

        // Initialize RecyclerView
        searchRecyclerView = view.findViewById(R.id.search_list_view)

        mealsAdapter = MealsAdapter( emptyList(), { meal ->
            searchViewModel.addMealToFavorites(meal)
            Toast.makeText(
                requireContext(),
                "${meal.strMeal} added to favorites",
                Toast.LENGTH_SHORT
            ).show()
        }, { meal ->
            searchViewModel.deleteFavoriteRecipe(meal)
            Toast.makeText(requireContext(), "${id} removed  from favorites", Toast.LENGTH_SHORT)
                .show()
        }, { meal ->
            Log.d("SearchFragment", "Navigating to details for meal: ${meal.idMeal}")
            val action = SearchFragmentDirections.actionSearchToRecipeDetailsFragment( meal.idMeal , 1 , meal.strMeal)
            findNavController().navigate(action)
        })
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = mealsAdapter
        et_email.editText?.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not used

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val newText = s.toString()

                    if (newText.isEmpty()) {
                        mealsAdapter.updateMeals(emptyList())
                    } else {
                        searchViewModel.searchMeals(newText)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not used
                }
            }
        )
        setupObservers()


        if (savedInstanceState?.getBoolean("restart") == true) {
            savedInstanceState.putBoolean("restart", false)
            Log.i("restart", "false")
//            mealsAdapter.updateMeals(emptyList(), searchViewModel.favoriteUserIds)
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenToUpdateFavouriteItems()

    }
    private fun setupObservers() {
        searchViewModel.search.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Error -> {
                    Snackbar.make(
                        requireView(),
                        R.string.error_loading_data,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                UiState.Loading -> {

                }

                is UiState.Success -> {
                    val mealsList = response.data
                    mealsAdapter.updateMeals(
                        mealsList
                    )  // recyclerview with new data
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        mealsAdapter.updateMeals(emptyList())
        listenToUpdateFavouriteItems()
        bundleOf().putBoolean("restart", true)
        Log.i("restart", "false")
    }


    private fun listenToUpdateFavouriteItems() {
        searchViewModel.favoriteUserIds.observe(viewLifecycleOwner) { response ->
            if (!response.isNullOrEmpty() ) {
                mealsAdapter.updateIDs(response as MutableList<String>)
//                favoriteRecipesAdapter.setData(viewModel.favoriteRecipes , viewModel.favoriteUserIds)
            }
        }
    }
}