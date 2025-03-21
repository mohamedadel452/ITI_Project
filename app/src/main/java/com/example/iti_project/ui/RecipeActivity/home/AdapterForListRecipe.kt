package com.example.iti_project.ui.RecipeActivity.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Meals

class AdapterForListRecipe(
    private val onItemClicked: (String,Int, String) -> Unit ,
) : RecyclerView.Adapter<AdapterForListRecipe.MyViewHolder>() {

    var meals = mutableListOf<Meals>()

    var favoriteUserIds = mutableListOf<String>()
    var favoriteUserRemovedIds = MediatorLiveData<Meals?>()
    var favoriteUserAddMeal = MediatorLiveData<Meals?>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mealName: TextView = view.findViewById(R.id.title_product)
        val mealImage: ImageView = view.findViewById(R.id.image_product)
        private val mealCategory: TextView = view.findViewById(R.id.catagory_of_meal)
        val favoriteImage: ImageView = view.findViewById(R.id.iv_save_to_favorit)
        var isFavorite : Boolean = false
        fun bind(meal: Meals) {
            mealName.text = meal.strMeal
            mealCategory.text = meal.strCategory
            Glide.with(mealImage.context).load(meal.strMealThumb).into(mealImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)

        if (favoriteUserIds.contains(meal.idMeal)) {
            Log.i("ids", meal.idMeal)
            holder.isFavorite = true
            holder.favoriteImage.setColorFilter(Color.argb(100, 255, 0, 0))
        }else{
            holder.favoriteImage.clearColorFilter()
        }

        holder.favoriteImage.setOnClickListener {

//            Glide.with(holder.favoriteImage.context).load(R.drawable.add).into(holder.favoriteImage)
            if (holder.isFavorite){
                holder.isFavorite = false
                holder.favoriteImage.clearColorFilter()
                favoriteUserRemovedIds.postValue(meal)
//                favoriteRecipeViewModel.deleteFavoriteRecipe(meal.idMeal)
            }else {
                holder.isFavorite = true
                holder.favoriteImage.setColorFilter(Color.argb(100, 255, 0, 0))
                favoriteUserAddMeal.postValue(meal)
                Log.i("RED ids", meal.idMeal)
//                favoriteRecipeViewModel.addFavoriteRecipe(meal)
            }

        }

        holder.mealImage.setOnClickListener {
            val id = meals[position].idMeal
            val count = meals[position].count
            val title= meals[position].strMeal
            if (id != null) onItemClicked(id, count , title)
        }

    }

    override fun getItemCount(): Int {
        return meals.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(meals: List<Meals> ) {
        this.meals = meals as MutableList<Meals>
//        favoriteUserIds = recipeIDs as MutableList<String>
//        Log.i("favoriteRecipeViewModel", "   " + meals.size)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateIDs(recipeIDs : MutableList<String> ) {
        favoriteUserIds = recipeIDs
        notifyDataSetChanged()
    }
}
