package com.example.iti_project.ui.RecipeActivity.HomeFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

    //private lateinit var searchBar: SearchBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog : ProgressDialog
   // private lateinit var searchInput: EditText
   private lateinit var searchView: SearchView

    private val viewModel: HomeFragmentViewModel by viewModels(){
        ProductViewModelFactory(MealsRepoImpl())
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

        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.list_recipe)
        mProgressDialog = ProgressDialog(requireContext())

        searchView.setOnClickListener{
          findNavController().navigate(R.id.search)
        }



        val adapter = AdapterForListRecipe()

        GlobalScope.launch(Dispatchers.Main) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }



        viewModel.getMeals()
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            when(meals){
                is UiState.Error ->
                    {
                        mProgressDialog.cancel()
                        Toast.makeText(requireContext(), "there is an error :${meals.errorMessage}", Toast.LENGTH_SHORT).show()
                        Log.e("error",meals.errorMessage)}
                is UiState.Loading ->{
                    mProgressDialog.setTitle("Loading Data")
                    mProgressDialog.setMessage("please wait while we are loading data")
                    mProgressDialog.show()
                    Toast.makeText(requireContext(), "loading ", Toast.LENGTH_SHORT).show()}
                is UiState.Success -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.setData(meals.data)
                    }
                    mProgressDialog.cancel()
                }

            }
    }


}

    override fun onStop() {
        super.onStop()
        // Update state in ViewModel
        Toast.makeText(requireContext(), "hi am on stop", Toast.LENGTH_SHORT).show()
        viewModel.setIsSaved(true)
    }
}