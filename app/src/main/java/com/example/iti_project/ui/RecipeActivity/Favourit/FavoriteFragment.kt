package com.example.iti_project.ui.RecipeActivity.Favourit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp


class FavoriteFragment : Fragment() {

    private lateinit var rv_favoriteRecipes: RecyclerView
    private lateinit var emptyState: TextView
    private lateinit var favoriteRecipesAdapter: FavoriteRecipesAdapter

    private val viewModel: FavoriteFragmentViewModel by viewModels() {
        FavoriteFragmentViewModelFactory(
            FavoriteRecipeRepoImp(
                LocalDataSourceImp(
                    requireContext()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        viewModel.getFavoriteList()
        return inflater.inflate(R.layout.fragment_favourit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_favoriteRecipes = view.findViewById(R.id.rv_favorite_recipe)
        emptyState = view.findViewById(R.id.empty_state_text)
        instantiateProductsRecyclerView()
        listenToDeleteFavouriteItems()
        listenToUpdateFavouriteItems()
    }

    private fun instantiateProductsRecyclerView() {
        favoriteRecipesAdapter = FavoriteRecipesAdapter { favoriteRecipeID,count , title->
            val action =
                FavoriteFragmentDirections.actionFavouritToRecipeDetailsFragment(favoriteRecipeID, count , title)
            findNavController().navigate(action)
        }
        rv_favoriteRecipes.apply {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoriteRecipesAdapter
        }
    }


    private fun listenToDeleteFavouriteItems() {
        if (favoriteRecipesAdapter.favoriteUserRemovedIds != null) {
            favoriteRecipesAdapter.favoriteUserRemovedIds.observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    viewModel.deleteFavoriteRecipe(response)
//                    viewModel.getFavoriteList()
//                favoriteRecipesAdapter.setData(viewModel.favoriteRecipes , viewModel.favoriteUserIds)
                }
            }
        }
        listenToUpdateFavouriteItems()
    }

    private fun listenToUpdateFavouriteItems() {

//        Log.i("response", "  " + "nooooooooooo")
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { response ->
//            Log.i("response", "  " + response.first().strMealThumb)
            if (!response.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "hi am here", Toast.LENGTH_SHORT).show()
                rv_favoriteRecipes.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
                favoriteRecipesAdapter.setData(
                    response as MutableList<Meals>
                )
            }
            else{
                rv_favoriteRecipes.visibility = View.INVISIBLE
                emptyState.visibility = View.VISIBLE
            }
        }

        viewModel.favoriteUserIds.observe(viewLifecycleOwner){ response ->
            if (!response.isNullOrEmpty()) {

                rv_favoriteRecipes.visibility = View.VISIBLE
                emptyState.visibility = View.GONE
                favoriteRecipesAdapter.setIDs(
                    response as MutableList<String>
                )
            }
            else{
                rv_favoriteRecipes.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            }
        }

    }
}