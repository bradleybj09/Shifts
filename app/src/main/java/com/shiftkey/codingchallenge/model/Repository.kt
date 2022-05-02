package com.shiftkey.codingchallenge.model

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiftkey.codingchallenge.ShiftsApi
import com.shiftkey.codingchallenge.util.DateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val shiftsApi: ShiftsApi,
    private val dateManager: DateManager,
    private val repositoryScope: CoroutineScope,
) {

    private val _shifts = MutableLiveData<List<Shift>>()
    val shifts: LiveData<List<Shift>> = _shifts

    private val address = "Dallas, TX"

    private val weeksLoaded = AtomicInteger(0)

    init {
        fetchInitialShifts()
    }

    private fun fetchInitialShifts() {
        repositoryScope.launch {
            _shifts.postValue(fetchShifts(address = address))
        }
    }

    private fun fetchShiftsForWeekStarting(start: String) {
        repositoryScope.launch {
            val newShifts = fetchShifts(start = start, address = address)
            val combined = combineShifts(_shifts.value.orEmpty(), newShifts)
            _shifts.postValue(combined)
        }
    }

    private fun combineShifts(old: List<Shift>, new: List<Shift>): List<Shift> {
        return old.toMutableList().apply {
            addAll(new.filterNot { it in old })
        }
    }

    fun fetchAdditionalShifts() {
        val weekToLoad = weeksLoaded.incrementAndGet()
        val startTime = dateManager.getIsoWeekForWeek(weekToLoad)
        fetchShiftsForWeekStarting(startTime)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun fetchShifts(
        type: String = "week",
        start: String? = null,
        end: String? = null,
        address: String? = null,
    ): List<Shift> = shiftsApi.getShiftsForWeekStarting(type, start, end, address).data.flatMap { it.shifts }
}