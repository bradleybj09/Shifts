package com.shiftkey.codingchallenge.view

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.shiftkey.codingchallenge.util.NavBus
import com.shiftkey.codingchallenge.model.Shift
import com.shiftkey.codingchallenge.model.Repository
import javax.inject.Inject

class ShiftListViewModel @Inject constructor(
    private val repository: Repository,
    private val navBus: NavBus,
): ViewModel(), ShiftListener {

    val shifts = repository.shifts

    override fun onClick(shift: Shift) {
        goToShift(shift.shiftId)
    }

    fun fetchAdditionalShifts() {
        repository.fetchAdditionalShifts()
    }

    private fun goToShift(shiftId: Long) = navBus.navigateToDetailFragment(shiftId)

    /**
     * this is needed to maintain our position in the list when we move between fragments
     */
    var listState: Parcelable? = null
}