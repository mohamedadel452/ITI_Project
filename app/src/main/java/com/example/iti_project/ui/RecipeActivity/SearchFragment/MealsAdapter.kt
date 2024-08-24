import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Meals

class MealsAdapter(
    private var favoriteMealsIds: Set<String>,
    private var mealsList: List<Meals>,
    private val onFavoriteClick: (Meals) -> Unit,
    private val onFavoriteClickToDelete: (String) -> Unit,
    private val onItemClicked: (Meals) -> Unit
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    private var _favoriteMeals = mutableSetOf<String>()

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealNameTextView: TextView = view.findViewById(R.id.meal_name_text_view)
        val mealthumb: ImageView = view.findViewById(R.id.mealthumbnail)
        val favoriteButton: ImageView = view.findViewById(R.id.iv_save_to_favorit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        _favoriteMeals = favoriteMealsIds as MutableSet<String>
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mealitem, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealsList[position]



        holder.mealNameTextView.text = meal.strMeal

        Glide.with(holder.mealNameTextView.context)
            .load(meal.strMealThumb)
            .into(holder.mealthumb)

        // Update favorite button color based on whether the meal is in the favorites list
        if (_favoriteMeals.contains(meal.idMeal)) {
            holder.favoriteButton.setColorFilter(Color.argb(100, 255, 0, 0))
        } else {
            holder.favoriteButton.clearColorFilter()
        }

        holder.favoriteButton.setOnClickListener {
            if (_favoriteMeals.contains(meal.idMeal)) {
                _favoriteMeals.remove(meal.idMeal)
                holder.favoriteButton.clearColorFilter()
                onFavoriteClickToDelete(meal.idMeal)

            } else {
                _favoriteMeals.add(meal.idMeal)
                holder.favoriteButton.setColorFilter(Color.argb(100, 255, 0, 0))
                onFavoriteClick(meal)
            }


        }
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("idMeal" to meal.idMeal)
            onItemClicked(meal)
        }
    }


        override fun getItemCount(): Int = mealsList.size

        fun updateMeals(newMealsList: List<Meals>, favoriteMealsIds: Set<String>) {
            _favoriteMeals = favoriteMealsIds as MutableSet<String>
            mealsList = newMealsList
            notifyDataSetChanged()  // Notifies RecyclerView
        }
    }


