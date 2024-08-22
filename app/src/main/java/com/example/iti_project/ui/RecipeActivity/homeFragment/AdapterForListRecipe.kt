package com.example.iti_project.ui.RecipeActivity.HomeFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Meals

class AdapterForListRecipe() : RecyclerView.Adapter<AdapterForListRecipe.MyViewHolder>() {

    var meals = listOf<Meals>()
    private var listener :(String)->Unit={}




    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mealName: TextView = view.findViewById(R.id.title_product)
        private val mealImage: ImageView = view.findViewById(R.id.image_product)
        private val mealCategory: TextView = view.findViewById(R.id.catagory_of_meal)

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
        holder.bind(meals[position])


     /*   holder.itemView.rootView.setOnClickListener {
            val id = meals[position].idMeal
            if (id != null) listener(id)
        }*/
    }

    override fun getItemCount(): Int {
        return meals.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(meals: List<Meals>) {
        this.meals = meals
        notifyDataSetChanged()
    }

  /*  fun setListener( listener:(String)->Unit){
        this.listener = listener
    }*/
}
