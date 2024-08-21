package com.example.iti_project.ui.RecipeActivity.SearchFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Meals

class MealsAdapter ( private val context: Context,
                     private var mealsList: List<Meals>
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {


    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealNameTextView: TextView = view.findViewById(R.id.meal_name_text_view)
        val mealthumb:ImageView = view.findViewById(R.id.mealthumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mealitem, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealsList[position]
        holder.mealNameTextView.text = meal.strMeal
        Glide.with(context)
            .load(meal.strMealThumb)
            .into(holder.mealthumb)
    }

    override fun getItemCount(): Int = mealsList.size


    fun updateMeals(newMealsList: List<Meals>) {
        mealsList = newMealsList
        notifyDataSetChanged()  // Notifies RecyclerView
    }
}