package com.shiftkey.codingchallenge.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavBus @Inject constructor() {

    private val _destination = MutableLiveData<Destination>(Destination.Default)
    val destination: LiveData<Destination> = _destination

    fun  navigateToDetailFragment(shiftId: Long) {
        _destination.postValue(Destination.ShiftDetail(shiftId))
    }

    fun resetDestination() {
        _destination.postValue(Destination.Default)
    }
}

sealed class Destination {
    object Default : Destination()
    data class ShiftDetail(val shiftId: Long) : Destination()
}