package com.example.iti_project.ui.RecipeActivity.DetailsFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iti_project.R

class AdapterForDetailsFragment : RecyclerView.Adapter<AdapterForDetailsFragment.MyViewHolder>() {

    private var ingredientName = listOf<String>()
    private var quantity = listOf<String>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvIngredientName = view.findViewById<TextView>(R.id.tvIngredientName)
        private val tvQuantity = view.findViewById<TextView>(R.id.tvQuantity)

        fun bind(name: String, quantity: String) {
            tvIngredientName.text = name
            tvQuantity.text = quantity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_ingredients, parent, false))
    }

    override fun getItemCount(): Int {
        return ingredientName.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(ingredientName[position], quantity[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(names: List<String>, quantities: List<String>) {
        // Filter out null or blank names
        val filteredData = names.zip(quantities).filter { (name, _) -> !name.isNullOrBlank() }
        // Separate the filtered data into two lists
        ingredientName = filteredData.map { it.first }
        quantity = filteredData.map { it.second }
        notifyDataSetChanged()
    }
}