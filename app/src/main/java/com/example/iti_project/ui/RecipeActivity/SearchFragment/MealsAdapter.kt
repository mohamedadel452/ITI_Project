import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iti_project.R
import com.example.iti_project.data.models.Meals

class MealsAdapter(
    private val context: Context,
    private var mealsList: List<Meals>,
    private val onFavoriteClick: (Meals) -> Unit
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    // Maintain a set of favorite meals IDs
    private val favoriteMeals = mutableSetOf<String>()

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealNameTextView: TextView = view.findViewById(R.id.meal_name_text_view)
        val mealthumb: ImageView = view.findViewById(R.id.mealthumbnail)
        val favoriteButton: ImageView = view.findViewById(R.id.iv_save_to_favorit)
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

        // Update favorite button color based on whether the meal is in the favorites list
        if (favoriteMeals.contains(meal.idMeal)) {
            holder.favoriteButton.setColorFilter(Color.argb(100, 255, 0, 0))
        } else {
            holder.favoriteButton
        }

        holder.favoriteButton.setOnClickListener {
            if (favoriteMeals.contains(meal.idMeal)) {
                favoriteMeals.remove(meal.idMeal)


            } else {
                favoriteMeals.add(meal.idMeal)
                holder.favoriteButton.setColorFilter(Color.argb(100, 255, 0, 0))
            }

            onFavoriteClick(meal)
        }
    }

    override fun getItemCount(): Int = mealsList.size

    fun updateMeals(newMealsList: List<Meals>) {
        mealsList = newMealsList
        notifyDataSetChanged()  // Notifies RecyclerView
    }
}
