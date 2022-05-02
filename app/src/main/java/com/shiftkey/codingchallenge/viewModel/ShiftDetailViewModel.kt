package com.shiftkey.codingchallenge.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.shiftkey.codingchallenge.model.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShiftDetailViewModel @AssistedInject constructor(
    repository: Repository,
    @Assisted shiftId: Long,
): ViewModel() {

    val shift = repository.shifts.map { shifts -> shifts.firstOrNull { it.shiftId == shiftId } }
    var bidViewVisibility = MutableLiveData(false)
    val countdown = MutableLiveData("")

    fun bidOnShift() {
        bidViewVisibility.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            countdown.postValue("5...")
            delay(1000)
            countdown.postValue("4...")
            delay(1000)
            countdown.postValue("3...")
            delay(1000)
            countdown.postValue("2...")
            delay(1000)
            countdown.postValue("1...")
            delay(1000)
            bidViewVisibility.postValue(false)
            countdown.postValue("")
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(shiftId: Long): ShiftDetailViewModel
    }

    companion object {
        fun createFactory(
            assistedFactory: AssistedFactory,
            shiftId: Long,
        ) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(shiftId) as T
            }

        }
    }
}