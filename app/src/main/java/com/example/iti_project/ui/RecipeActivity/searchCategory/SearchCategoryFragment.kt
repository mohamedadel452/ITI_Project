package com.example.iti_project.ui.RecipeActivity.searchCategory

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.CategoryRepo.CategoryRepoImp
import com.example.iti_project.ui.RecipeActivity.category.CategoryViewModelFactory
import com.google.android.material.search.SearchBar
import com.google.android.material.snackbar.Snackbar


class SearchCategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var adapter: AdapterForSearchMealsByCategory
    private lateinit var categoryName: TextView
    private lateinit var searchBar: SearchBar
    private lateinit var emptyState: TextView
    private val args: SearchCategoryFragmentArgs by navArgs()

    private val viewModel: SearchCategoryViewModel by viewModels() {
        SearchCategoryViewModelFactory(
            CategoryRepoImp()
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.list_meals_by_category_name)
        searchBar=view.findViewById(R.id.search_bar)
        categoryName = view.findViewById(R.id.tvCategory)
        emptyState = view.findViewById(R.id.empty_state_text)
        args.categoryName.let {
            categoryName.text = "Explore $it"
        }
        searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_searchCategoryFragment_to_search)
        }

        mProgressDialog = ProgressDialog(requireContext())
          adapter = AdapterForSearchMealsByCategory{id ->
              val action = SearchCategoryFragmentDirections.actionSearchCategoryFragmentToRecipeDetailsFragment(id)
              findNavController().navigate(action)
          }
        recyclerView.adapter = adapter
/*
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
*/
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

       val categoryName: String? = args.categoryName

        categoryName?.let {it->
            viewModel.getMealsByCategoryName(it)
        }

        viewModel.meals.observe(viewLifecycleOwner) { mealByCategory ->
            when (mealByCategory) {
                is UiState.Error -> {
                    mProgressDialog.cancel()

                    Snackbar.make(
                        requireView(),
                        R.string.error_loading_data,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.retry) {
                        categoryName?.let {
                            viewModel.getMealsByCategoryName(it)
                        }
                    }.show()
                }

                is UiState.Loading -> {
                    mProgressDialog.setTitle("Loading Data")
                    mProgressDialog.setMessage("please wait while we are loading data")
                    mProgressDialog.show()
                    Toast.makeText(requireContext(), "loading ", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    mProgressDialog.cancel()
                    if (mealByCategory.data.isEmpty()) {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.INVISIBLE

                    } else {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.setData(mealByCategory.data)
                    }


                }

            }
        }

    }


}