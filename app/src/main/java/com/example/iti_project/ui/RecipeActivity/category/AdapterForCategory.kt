package com.example.iti_project.ui.RecipeActivity.category

import android.annotation.SuppressLint
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
import com.example.iti_project.data.models.Category
import com.example.iti_project.data.models.Meals
class AdapterForCategory (//private val onItemClicked: (String,Int) -> Unit ,
) : RecyclerView.Adapter<AdapterForCategory.MyViewHolder>() {

    var categories = mutableListOf<Category>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryName: TextView = view.findViewById(R.id.title_category)
        val categoryImage: ImageView = view.findViewById(R.id.image_category)
        fun bind(category: Category) {
            categoryName.text = category.strCategory
            Glide.with(categoryImage.context).load(category.strCategoryThumb).into(categoryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_catagory, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)




     /*   holder.mealImage.setOnClickListener {
            val id = categories[position].idMeal
            val count = categories[position].count
            if (id != null) onItemClicked(id, count )
        }*/

    }

    override fun getItemCount(): Int {
        return categories.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(categories: List<Category> ) {
        this.categories = categories as MutableList<Category>
        notifyDataSetChanged()
    }

}