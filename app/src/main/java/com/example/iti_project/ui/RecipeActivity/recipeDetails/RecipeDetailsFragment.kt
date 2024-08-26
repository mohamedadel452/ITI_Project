package com.example.iti_project.ui.RecipeActivity.recipeDetails

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.MealsDetails
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.Meals.MealsRepoImpl
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import com.example.iti_project.ui.RecipeActivity.DetailsFragment.AdapterForDetailsFragment
import com.example.iti_project.ui.RecipeActivity.DetailsFragment.DetailsViewModel
import com.example.iti_project.ui.RecipeActivity.DetailsFragment.DetailsViewModelFactory
import com.google.android.material.snackbar.Snackbar


class RecipeDetailsFragment : Fragment() {

    private val args:RecipeDetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels(){

        DetailsViewModelFactory(MealsRepoImpl(),
            FavoriteRecipeRepoImp(
                LocalDataSourceImp(
                    requireContext(),
                    RoomDataBaseImp.getInstance(requireContext()),
                    SharedPreferenceImp.getInstance(requireContext())
                )
            )
        )
    }
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var recipeImage : ImageView
    private lateinit var play_video : ImageView
    private lateinit var recipeTitle : TextView
    private lateinit var author_image : ImageView
    private lateinit var recipyCatagory : TextView
    private lateinit var recipyArea : TextView
    private lateinit var add_to_fav : ImageView
    private lateinit var showIngredient : Button
    private lateinit var showInstructions : Button
    private lateinit var recipeDescription : TextView
    private lateinit var rcv_ingredients : RecyclerView
    private lateinit var allInstructions: ScrollView
    private lateinit var allIngredients: ScrollView
    private lateinit var ingredientsAdapter: AdapterForDetailsFragment
    private  var strYoutube : String? = null

    private lateinit var imageContainer: RelativeLayout
    private lateinit var webView : WebView

    private lateinit var meal : MealsDetails

    private lateinit var webView_dialog : Dialog

    private lateinit var recipeDetailsFragment : ScrollView

    private var favoritelistIds : List<String> = listOf()

    val colorVisible = "#FFED6E3A" // Color when button is visible
    val colorNotVisible = "#FFB6BAB6" // Color when button is not visible
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetails(args.idMeal)

        recipeImage=view.findViewById(R.id.recipe_image)
        play_video=view.findViewById(R.id.play_video)
        recipeTitle=view.findViewById(R.id.recipe_title)
        recipyCatagory=view.findViewById(R.id.recipy_catagory)
        author_image=view.findViewById(R.id.author_image)
        recipyArea=view.findViewById(R.id.area)
        add_to_fav=view.findViewById(R.id.add_to_fav)
        showIngredient=view.findViewById(R.id.show_Ingredients)
        showInstructions=view.findViewById(R.id.show_instruction)
        recipeDescription=view.findViewById(R.id.recipe_description)
        rcv_ingredients=view.findViewById(R.id.recyclerView_Ingredients)
        allInstructions=view.findViewById(R.id.scroll_view_Instructions)
        allIngredients=view.findViewById(R.id.scroll_view_Ingredients)
        webView_dialog = Dialog(requireContext())
        imageContainer = view.findViewById(R.id.image_container)
        recipeDetailsFragment = view.findViewById(R.id.recipe_details_scroll_view)
        mProgressDialog = ProgressDialog(requireContext())

        listenToUpdateFavouriteItems()

        showInstructions.setOnClickListener {
            if (allInstructions.visibility == View.GONE) {
                allInstructions.visibility = View.VISIBLE

                allIngredients.visibility = View.GONE

                // Set background colors
                showIngredient.setBackgroundColor(Color.parseColor(colorNotVisible))
                showInstructions.setBackgroundColor(Color.parseColor(colorVisible))
            } else {
                allInstructions.visibility = View.GONE
                allIngredients.visibility = View.VISIBLE
                // Set background colors
                showIngredient.setBackgroundColor(Color.parseColor(colorVisible))
                showInstructions.setBackgroundColor(Color.parseColor(colorNotVisible))
            }
        }

        showIngredient.setOnClickListener {
            if (allIngredients.visibility == View.GONE) {
                allIngredients.visibility = View.VISIBLE
                allInstructions.visibility = View.GONE

                // Set background colors
                showIngredient.setBackgroundColor(Color.parseColor(colorVisible))
                showInstructions.setBackgroundColor(Color.parseColor(colorNotVisible))
            } else {
                allIngredients.visibility = View.GONE
                allInstructions.visibility = View.VISIBLE
                // Set background colors
                showIngredient.setBackgroundColor(Color.parseColor(colorNotVisible))
                showInstructions.setBackgroundColor(Color.parseColor(colorVisible))
            }
        }


        add_to_fav.setOnClickListener {
            if (add_to_fav.contentDescription  == "Add to favorites") {
                // Change the button text to "Added"
                viewModel.addFavoriteRecipe(meal,args.count)
                add_to_fav.contentDescription  = "Added"
                add_to_fav.setColorFilter(Color.argb(100, 255, 0, 0))

            } else {
                // Change the button text back to "Add"
                viewModel.deleteFavoriteRecipe(meal,args.count)
                add_to_fav.contentDescription = "Add to favorites"
                add_to_fav.clearColorFilter()
            }
        }

        play_video.setOnClickListener {
            if (imageContainer.visibility == View.VISIBLE&& strYoutube!=null) {
                showWebViewDialog(strYoutube)

            }
            else{
                Snackbar.make(
                    requireView(),
                    getString(R.string.error_message_video_link),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.mealDetails.observe(viewLifecycleOwner){

            when(it){
                is UiState.Error -> {
                    mProgressDialog.cancel()
                    Toast.makeText(requireContext(), "error ", Toast.LENGTH_SHORT).show()
                    Glide.with(recipeImage.context).load(R.drawable.error_image).into(recipeImage)
                    add_to_fav.isClickable = false
                    play_video.visibility = View.GONE
                }
                is UiState.Loading ->{
                    mProgressDialog.setTitle("Loading Data")
                    mProgressDialog.setMessage("please wait while we are loading data")
                    mProgressDialog.show()
                    Toast.makeText(requireContext(), "loading ", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    setData(it.data)
                    meal = it.data
                    play_video.visibility = View.VISIBLE
                    listenToUpdateFavouriteItems()
                    if(favoritelistIds != null)
                        if (favoritelistIds.contains(meal.idMeal) == true){
                            add_to_fav.contentDescription = "Added"
                            add_to_fav.setColorFilter(Color.argb(100, 255, 0, 0))
                        }
                    add_to_fav.isClickable = true
                    mProgressDialog.cancel()
                }
            }


        }

        ingredientsAdapter= AdapterForDetailsFragment()
        rcv_ingredients.adapter=ingredientsAdapter
        rcv_ingredients.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

    }

    private fun showWebViewDialog(strYoutube: String?) {
        webView_dialog.setContentView(R.layout.web_view_dialog)
        webView_dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        webView_dialog.setCancelable(true)
        webView_dialog.window!!.attributes.windowAnimations = R.style.animation

        webView = webView_dialog.findViewById(R.id.webView)
        webView?.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.pluginState = WebSettings.PluginState.ON

            strYoutube?.let { thelink -> loadUrl(thelink) }
            recipeDetailsFragment.alpha=0.5f

        }
        webView_dialog.show()

        webView_dialog.findViewById<Button>(R.id.cancelVideo).setOnClickListener {
            webView_dialog.dismiss()
            webView.loadUrl("about:blank")
            recipeDetailsFragment.alpha=1f
        }



    }

    private fun setData(meal: MealsDetails){

        Glide.with(requireContext())
            .load(meal.strMealThumb)
            .into(recipeImage)
        recipeTitle.text=meal.strMeal
        recipyCatagory.text=meal.strCategory
        Glide.with(requireContext())
            .load(meal.strMealThumb)
            .into(author_image)
        recipyArea.text=meal.strArea
        recipeDescription.text=meal.strInstructions
        strYoutube=meal.strYoutube

        val ingredients = listOfNotNull(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5,
            meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
            meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
            meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
        )

        val measures = listOfNotNull(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5, meal.strMeasure6,
            meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16, meal.strMeasure17, meal.strMeasure18,
            meal.strMeasure19, meal.strMeasure20
        )

        ingredientsAdapter.setData(ingredients,measures)


    }
    private fun listenToUpdateFavouriteItems() {
        viewModel.favoriteUserIds.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) favoritelistIds = it

        }
    }
}