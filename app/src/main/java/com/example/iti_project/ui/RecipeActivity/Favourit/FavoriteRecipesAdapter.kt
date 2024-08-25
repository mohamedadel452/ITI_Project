package com.example.iti_project.ui.RecipeActivity.Favourit

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


class FavoriteRecipesAdapter(private val onItemClicked: (String,Int) -> Unit ) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    var meals = mutableListOf<Meals>()
    var favoriteUserRemovedIds = MediatorLiveData<Meals?>()
    private var favoriteIdsList : MutableList<String> = mutableListOf()
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mealName: TextView = view.findViewById(R.id.title_product)
        val mealImage: ImageView = view.findViewById(R.id.image_product)
        private val mealCategory: TextView = view.findViewById(R.id.catagory_of_meal)
        val favoriteImage: ImageView = view.findViewById(R.id.iv_save_to_favorit)
        fun bind(meal: Meals) {
            mealName.text = meal.strMeal
            mealCategory.text = meal.strCategory
            Glide.with(mealImage.context).load(meal.strMealThumb).into(mealImage)
            favoriteImage.setColorFilter(Color.argb(100, 255, 0, 0))
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
        Log.i("show", meal.strMealThumb)
        holder.favoriteImage.setOnClickListener {
//            favoriteUserRemovedIdsList.add(meal.idMeal)
//            Glide.with(holder.favoriteImage.context).load(R.drawable.add).into(holder.favoriteImage)
            favoriteUserRemovedIds.postValue(meal)
            meals.removeAt(position)
            notifyDataSetChanged()
//            favoriteUserRemovedIds.postValue(null)
        }

        holder.mealImage.setOnClickListener {
            val id = meals[position].idMeal
            val count = meals[position].count
            if (id != null) onItemClicked(id,count)
        }


    }

    override fun getItemCount(): Int {
        return meals.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(meals: MutableList<Meals> ) {
        this.meals = meals
        Log.i("favoriteRecipeViewModel", "   " + meals.size)
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIDs(favoriteIdsList: MutableList<String> ) {
        this.favoriteIdsList = favoriteIdsList
        Log.i("favoriteRecipeViewModel", "   " + favoriteIdsList.size)
        notifyDataSetChanged()

    }

}