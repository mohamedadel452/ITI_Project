package com.example.iti_project.ui.RecipeActivity.Favourit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.Meals
import com.example.iti_project.data.repo.favouriteRepo.FavoriteRecipeRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteRecipesAdapter(private val onItemClicked: (String) -> Unit ) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    var meals = mutableListOf<Meals>()

//    private lateinit var favoriteRecipeViewModel: FavoriteFragmentViewModel


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mealName: TextView = view.findViewById(R.id.title_product)
        val mealImage: ImageView = view.findViewById(R.id.image_product)
        private val mealCategory: TextView = view.findViewById(R.id.catagory_of_meal)
        val favoriteImage: ImageView = view.findViewById(R.id.iv_save_to_favorit)
        var isFavorite: Boolean = false
        fun bind(meal: Meals) {
            mealName.text = meal.strMeal
            mealCategory.text = meal.strCategory
            Glide.with(mealImage.context).load(meal.strMealThumb).into(mealImage)

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)

        holder.favoriteImage.setOnClickListener {

//            Glide.with(holder.favoriteImage.context).load(R.drawable.add).into(holder.favoriteImage)
            if (holder.isFavorite) {
                holder.isFavorite = false
                holder.favoriteImage.clearColorFilter()
//                favoriteRecipeViewModel.deleteFavoriteRecipe(meal.idMeal)
                meals.removeAt(position)
            }
        }

        holder.mealImage.setOnClickListener {
            val id = meals[position].idMeal
            if (id != null) onItemClicked(id)
        }

    }

    override fun getItemCount(): Int {
        return meals.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(meals: MutableList<Meals>) {
        this.meals = meals
        Log.i("favoriteRecipeViewModel", "   " + meals.size)
        notifyDataSetChanged()


    }


}
