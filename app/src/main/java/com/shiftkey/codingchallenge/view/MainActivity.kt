package com.shiftkey.codingchallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.shiftkey.codingchallenge.util.Destination
import com.shiftkey.codingchallenge.NavDirections
import com.shiftkey.codingchallenge.R
import com.shiftkey.codingchallenge.viewModel.ViewModelFactory
import com.shiftkey.codingchallenge.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>
    private val viewModel: MainViewModel by viewModels { factory }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController)

        viewModel.navDestination.observe(this) { destination ->
            when (destination) {
                Destination.Default -> Unit // do nothing, this is my SLE solution
                is Destination.ShiftDetail -> navController.navigate(NavDirections.actionGlobalDetailFragment(destination.shiftId))
            }
            if (destination != Destination.Default) {
                viewModel.resetDestination()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}