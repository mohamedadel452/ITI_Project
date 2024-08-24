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
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class SearchFragment : Fragment() {

    private lateinit var favoriteRepo: FavoriteRecipeRepoImp
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView

    private val searchViewModel: SearchViewModel by viewModels {

        SearchViewModel.SearchViewModelFactory(MealsRepoImpl(RetrofitClient),favoriteRepo)    }

    override fun onStart() {
        super.onStart()
        searchViewModel.getFavoriteList()

    }
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

        // Initialize SearchBar and SearchView
        searchBar = view.findViewById(R.id.search_bar)
        searchView = view.findViewById(R.id.sr4vw)


        // Initialize RecyclerView
        searchRecyclerView = view.findViewById(R.id.search_list_view)

        mealsAdapter = MealsAdapter(searchViewModel.favoriteUserIds , emptyList(),{ meal ->
            searchViewModel.addMealToFavorites(meal)
            Toast.makeText(requireContext(), "${meal.strMeal} added to favorites", Toast.LENGTH_SHORT).show()
        }, { id ->
            searchViewModel.deleteFavoriteRecipe(id)
            Toast.makeText(requireContext(), "${id} removed  from favorites", Toast.LENGTH_SHORT).show()
        }, { meal ->
            Log.d("SearchFragment", "Navigating to details for meal: ${meal.idMeal}")
            val bundle = bundleOf("idMeal" to meal.idMeal)
            findNavController().navigate(R.id.action_search_to_recipeDetailsFragment,bundle )
        })
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = mealsAdapter

        setupObservers()
        searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()
                if (newText.isEmpty()) {
                    mealsAdapter.updateMeals(emptyList(), searchViewModel.favoriteUserIds)
                } else {
                    searchViewModel.searchMeals(newText)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used
            }
        })
        if(savedInstanceState?.getBoolean("restart") == true){
            savedInstanceState.putBoolean("restart" , false)
            Log.i("restart", "false")
//            mealsAdapter.updateMeals(emptyList(), searchViewModel.favoriteUserIds)
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchView.requestFocus()

        // Show keyboard
        showKeyboard()
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
    override fun onPause() {
        super.onPause()
        searchViewModel.getFavoriteList()
        mealsAdapter.updateMeals(emptyList(), searchViewModel.favoriteUserIds)
        bundleOf().putBoolean("restart" , true)

        Log.i("restart", "false")
    }
}
