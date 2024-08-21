package com.example.iti_project.ui.RecipeActivity.HomeFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class HomeFragment : Fragment() {

    private lateinit var searchBar: SearchBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterForListRecipe
    private val viewModel: HomeFragmentViewModel by viewModels() {
        ProductViewModelFactory(MealsRepoImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.list_recipe)
        val progressBar =ProgressDialog(requireContext())

        adapter = AdapterForListRecipe()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        searchBar.setOnClickListener {
            findNavController().navigate(R.id.search)
        }
        searchBar.menu.getItem(0).setOnMenuItemClickListener {
            findNavController().navigate(R.id.search)
            true
        }


        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            when (meals) {
                is UiState.Error -> {
                    progressBar.cancel()
                    Toast.makeText(requireContext(), "Error: ${meals.errorMessage}", Toast.LENGTH_SHORT).show()
                    Log.e("error", meals.errorMessage)
                }
                is UiState.Loading -> {
                    progressBar.setTitle("Loading Data")
                    progressBar.setMessage("please wait while we are loading data")
                    progressBar.show()
                }
                is UiState.Success -> {
                    adapter.setData(meals.data)
                    progressBar.cancel()
                }
            }
        }

        // Trigger data loading
        viewModel.getMeals()
    }
}
