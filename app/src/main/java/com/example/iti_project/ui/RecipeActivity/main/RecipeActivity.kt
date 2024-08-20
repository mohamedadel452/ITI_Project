package com.example.iti_project.ui.RecipeActivity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {


    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    private val viewModel: RecipeActivityVIewModel by viewModels(){
        RecipeActivityVIewModelFactory(UserRepoImp(LocalDataSourceImp(this)))
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        bottomNavView=findViewById(R.id.nav_view)
        navController=(supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHost).navController
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottomNavView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(navGraph = navController.graph))


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.recipe_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.logout -> {
                logout()
                return true
            }

            R.id.about -> {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.about)
                true

            }

            else -> false
        }

    }

    private fun logout() {

        viewModel.setLoggedIn(false)
        viewModel.LoginState.observe(this){
            if (it){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}