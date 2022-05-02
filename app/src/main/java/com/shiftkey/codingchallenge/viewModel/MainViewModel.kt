package com.shiftkey.codingchallenge.viewModel

import androidx.lifecycle.ViewModel
import com.shiftkey.codingchallenge.util.NavBus
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val navBus: NavBus,
) : ViewModel() {

    val navDestination = navBus.destination
    fun resetDestination() = navBus.resetDestination()
}