package com.example.iti_project.ui.RecipeActivity.homeFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl


import com.google.android.material.search.SearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var sr4btn :ImageButton
    //private lateinit var searchBar: SearchBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog: ProgressDialog
    private val viewModel: HomeFragmentViewModel by viewModels() {
        ProductViewModelFactory(MealsRepoImpl())
    }
    private lateinit var adapter: AdapterForListRecipe

    override fun onStart() {
        super.onStart()
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
        //searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.list_recipe)
        mProgressDialog = ProgressDialog(requireContext())
        sr4btn=view.findViewById(R.id.imageButton)
        adapter = AdapterForListRecipe({
            val action = HomeFragmentDirections.actionHomeToRecipeDetailsFragment(it)
            findNavController().navigate(action)
        }, requireContext())
        sr4btn.setOnClickListener {
            findNavController().navigate(R.id.search)
        }
        /*
        sr4btn.menu.getItem(0).setOnMenuItemClickListener {
            findNavController().navigate(R.id.search)
            true
        }
*/

        GlobalScope.launch(Dispatchers.Main) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }

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
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.setData(meals.data)
                        delay(2000)
                    }
                    mProgressDialog.cancel()
                }

            }
        }


    }

}