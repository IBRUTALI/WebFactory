package com.example.webfactory.presentation.main

import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.webfactory.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MainActivityViewModelFactory(this))
            .get(MainActivityViewModel::class.java)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_calendar, R.id.nav_forum, R.id.nav_polls
        )
            .setOpenableLayout(drawer)
            .build()
        navController = findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController!!, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController!!)
    }

    //Inflate menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> {
                viewModel.out()
                navController?.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
}