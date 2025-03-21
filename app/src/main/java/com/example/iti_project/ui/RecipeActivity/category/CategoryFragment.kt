package com.example.iti_project.ui.RecipeActivity.category

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.CategoryRepo.CategoryRepoImp
import com.google.android.material.snackbar.Snackbar

class CategoryFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var adapter: AdapterForCategory
    private lateinit var emptyState: TextView

    private val viewModel: CategoryViewModel by viewModels() {
        CategoryViewModelFactory(
            CategoryRepoImp()
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.list_categories)
        emptyState = view.findViewById(R.id.empty_state_text)
        mProgressDialog = ProgressDialog(requireContext())
        adapter = AdapterForCategory{categoryName ->
            val action = CategoryFragmentDirections.actionCategoryFragmentToSearchCategoryFragment(categoryName)
            findNavController().navigate(action)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        viewModel.getCategories()
        viewModel.categories.observe(viewLifecycleOwner) { category ->
            when (category) {
                is UiState.Error -> {
                    mProgressDialog.cancel()

                    Snackbar.make(
                        requireView(),
                        R.string.error_loading_data,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.retry) {
                        viewModel.getCategories()
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
                    if (category.data.isEmpty()) {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.setData(category.data)
                    }

                }

            }
        }

    }


}