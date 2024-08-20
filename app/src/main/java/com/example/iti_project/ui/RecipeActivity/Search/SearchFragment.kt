package com.example.iti_project.ui.RecipeActivity.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.RemoteDataSource.RetrofitClient
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl


class SearchFragment : Fragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchBackButton: ImageButton
    private lateinit var searchListView: ListView

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.SearchViewModelFactory(MealsRepoImpl(RetrofitClient))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchEditText = view.findViewById(R.id.search_edit_text)
        searchBackButton = view.findViewById(R.id.search_back_btn)
        searchListView = view.findViewById(R.id.search_list_view)

        setupObservers()

        searchBackButton.setOnClickListener {
            // Handle back button click, maybe navigate back or clear search
            requireActivity().onBackPressed()
        }
        searchEditText.setOnEditorActionListener { _, _, _ ->
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                searchViewModel.searchMeals(query)
            } else {
                Toast.makeText(requireContext(), "Please enter a meal", Toast.LENGTH_SHORT).show()
            }
            true
        }

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
                    //loading indicator
                }

                is UiState.Success -> {
                    val mealsList = response.data
//                    val adapter = MealsAdapter(requireContext(), mealsList)
//                    searchListView.adapter = adapter
                }
            }
        }
    }
}

