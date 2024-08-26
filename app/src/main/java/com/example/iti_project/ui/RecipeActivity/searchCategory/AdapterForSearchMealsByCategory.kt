package com.example.iti_project.ui.RecipeActivity.searchCategory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Category
import com.example.iti_project.data.models.CategoryMealResponse

class AdapterForSearchMealsByCategory (private val onItemClicked: (String) -> Unit ,
) : RecyclerView.Adapter<AdapterForSearchMealsByCategory.MyViewHolder>() {

    var mealFiltered = mutableListOf<CategoryMealResponse>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mealFilteredName: TextView = view.findViewById(R.id.title_category)
        val mealFilteredImage: ImageView = view.findViewById(R.id.image_category)
        fun bind(mealFiltered: CategoryMealResponse) {
            mealFilteredName.text = mealFiltered.strMeal
            Glide.with(mealFilteredImage.context).load(mealFiltered.strMealThumb).into(mealFilteredImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_catagory, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = mealFiltered[position]
        holder.bind(category)




           holder.mealFilteredImage.setOnClickListener {
               val id = mealFiltered[position].idMeal
               if (id != null) onItemClicked(id)
           }

    }

    override fun getItemCount(): Int {
        return mealFiltered.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(mealFiltered: List<CategoryMealResponse> ) {
        this.mealFiltered = mealFiltered as MutableList<CategoryMealResponse>
        notifyDataSetChanged()
    }

}