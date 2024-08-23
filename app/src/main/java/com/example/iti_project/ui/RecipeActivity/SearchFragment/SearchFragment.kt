package com.example.iti_project.ui.RecipeActivity.SearchFragment

import MealsAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.RemoteDataSource.RetrofitClient
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.google.android.material.search.SearchBar
import androidx.appcompat.widget.SearchView
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDatabaseInterface
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceInterface
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp

class SearchFragment : Fragment() {

    private lateinit var favoriteRepo: FavoriteRecipeRepoImp
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var searchView: SearchView

    private val searchViewModel: SearchViewModel by viewModels {

        SearchViewModel.SearchViewModelFactory(MealsRepoImpl(RetrofitClient),favoriteRepo)    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize RoomDataSource (example using Room)
        val roomDataSource: RoomDatabaseInterface = RoomDataBaseImp.getInstance(requireContext())

        // Initialize SharedPreferencesDataSource using your SharedPreferenceImp class
        val sharedPreferencesDataSource: SharedPreferenceInterface = SharedPreferenceImp.getInstance(requireContext())

        // Initialize LocalDataSourceImp
        val localDataSource = LocalDataSourceImp(requireContext(), roomDataSource, sharedPreferencesDataSource)

        // Initialize favoriteRepo
        favoriteRepo = FavoriteRecipeRepoImp(localDataSource)

        searchView = view.findViewById(R.id.search_view)
        searchRecyclerView = view.findViewById(R.id.search_list_view)

        // Initialize RecyclerView
        mealsAdapter = MealsAdapter(searchViewModel.favoriteUserIds , emptyList(),{ meal ->
            searchViewModel.addMealToFavorites(meal)
            Toast.makeText(requireContext(), "${meal.strMeal} added to favorites", Toast.LENGTH_SHORT).show()
        }, { id ->
            searchViewModel.deleteFavoriteRecipe(id)
            Toast.makeText(requireContext(), "${id} added to favorites", Toast.LENGTH_SHORT).show()
        })
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = mealsAdapter

        setupObservers()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    mealsAdapter.updateMeals(emptyList(), searchViewModel.favoriteUserIds) // Clear the list when input is cleared
                } else {

                    searchViewModel.searchMeals(newText)
                }
                return true
            }
        })

        showKeyboard()
        return view
    }

    private fun setupObservers() {
        searchViewModel.search.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Error -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }

                UiState.Loading -> {

                }

                is UiState.Success -> {
                    val mealsList = response.data
                    mealsAdapter.updateMeals(mealsList, searchViewModel.favoriteUserIds )  // recyclerview with new data
                }
            }
        }
    }
    private fun showKeyboard() {
        searchView.requestFocus()  // Request focus on the SearchView
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
    }
}
