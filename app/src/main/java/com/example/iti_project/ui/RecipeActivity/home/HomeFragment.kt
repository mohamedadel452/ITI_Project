package com.example.iti_project.ui.RecipeActivity.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.google.android.material.search.SearchBar
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var weeklyRecipe_Img: ImageView
    private lateinit var weeklyRecipe_Name: TextView
    private lateinit var see_Weeklymore: ImageView
    private lateinit var see_Categories: ImageView
    private var weekly_id: String? = null

    private val viewModel: HomeFragmentViewModel by viewModels() {
        ProductViewModelFactory(
            MealsRepoImpl(), FavoriteRecipeRepoImp(
                LocalDataSourceImp(
                    requireContext()
                )
            )
        )
    }
    private lateinit var adapter: AdapterForListRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isNetworkConnected()) {
            showNetworkErrorDialog()
        } else {
            viewModel.getMeals()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_recipe)
        weeklyRecipe_Img = view.findViewById(R.id.weeklyPickImage)
        weeklyRecipe_Name = view.findViewById(R.id.weeklyPickSubtitle)
        see_Weeklymore = view.findViewById(R.id.weeklyPickPlayButton)
        see_Categories = view.findViewById(R.id.ivDiscoverMore)
        see_Categories.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToCategoryFragment()
            findNavController().navigate(action)
        }
        see_Weeklymore.setOnClickListener {
            weekly_id?.let { nonNullWeeklyId ->
                val action = HomeFragmentDirections.actionHomeToRecipeDetailsFragment(nonNullWeeklyId)
                findNavController().navigate(action)
            }


        }
        weeklyRecipe_Img.setOnClickListener {
            weekly_id?.let { nonNullWeeklyId ->
                val action = HomeFragmentDirections.actionHomeToRecipeDetailsFragment(nonNullWeeklyId)
                findNavController().navigate(action)
            }


        }
        mProgressDialog = ProgressDialog(requireContext())
        adapter = AdapterForListRecipe{id , count , title->
            val action = HomeFragmentDirections.actionHomeToRecipeDetailsFragment( id , count , title)
            findNavController().navigate(action)
        }


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        viewModel.getMeals()
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            when (meals) {
                is UiState.Error -> {
                    mProgressDialog.cancel()

                    Snackbar.make(
                        requireView(),
                        R.string.error_loading_data,
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(R.string.retry) {
                        viewModel.getMeals()
                    }.show()
                }

                is UiState.Loading -> {
                    mProgressDialog.setTitle("Loading Data")
                    mProgressDialog.setMessage("please wait while we are loading data")
                    mProgressDialog.show()
                    Toast.makeText(requireContext(), "loading ", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    adapter.setData(meals.data)
                    val random = meals.data.random()
                    Glide.with(requireContext()).load(random.strMealThumb).into(weeklyRecipe_Img)
                    weeklyRecipe_Name.text = random.strMeal
                    weekly_id = random.idMeal
                    mProgressDialog.cancel()
                }

            }
        }
        listenToUpdateFavouriteItems()
        listenToDeleteFavouriteItems()
        listenToAddFavouriteItems()

    }

    private fun listenToDeleteFavouriteItems() {
        adapter.favoriteUserRemovedIds.observe(viewLifecycleOwner) { response ->
            if (response != null ) {
                viewModel.deleteFavoriteRecipe(response)
            }
        }
    }

    private fun listenToAddFavouriteItems() {
        adapter.favoriteUserAddMeal.observe(viewLifecycleOwner) { response ->
            if (response != null ) {
                viewModel.addFavoriteRecipe(response)

            }
        }
    }
    private fun listenToUpdateFavouriteItems() {
        viewModel.favoriteUserIds.observe(viewLifecycleOwner) { response ->
            adapter.updateIDs(response as MutableList<String>)

        }
    }
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    private fun showNetworkErrorDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("No network connection available. Please check your internet settings.")
            .setTitle("Network Error")
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}