package com.shiftkey.codingchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.shiftkey.codingchallenge.model.*
import com.shiftkey.codingchallenge.util.DateManager
import com.shiftkey.codingchallenge.util.NavBus
import com.shiftkey.codingchallenge.view.ShiftListViewModel
import com.shiftkey.codingchallenge.viewModel.ShiftDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ViewModelTests {
    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    private lateinit var listViewModel: ShiftListViewModel
    private lateinit var detailViewModel: ShiftDetailViewModel

    private val shift1 = Shift(
        1,
        "2022-04-29T18:00:00+00:00",
        "2022-04-30T02:00:00+00:00",
        "2022-04-29 14:00:00",
        "2022-04-29 22:00:00",
        "Eastern",
        false,
        false,
        "Evening Shift",
        90,
        FacilityType(8, "Skilled Nursing Facility", "#AF52DE"),
        Skill(2, "Long Term Care", "#007AFF"),
        LocalizedSpecialty(
            39,
            6,
            39,
            "Certified Nursing Aide",
            "CNA",
            Specialty(6, "Certified Nursing Aide", "#007AFF", "CNA")
        )
    )

    private val shift2 = Shift(
        2,
        "2022-04-29T18:00:00+00:00",
        "2022-04-30T02:00:00+00:00",
        "2022-04-29 14:00:00",
        "2022-04-29 22:00:00",
        "Eastern",
        true,
        false,
        "Evening Shift",
        90,
        FacilityType(8, "Unskilled Nursing Facility", "#AF52DE"),
        Skill(2, "Long Term Care", "#007AFF"),
        LocalizedSpecialty(
            39,
            6,
            39,
            "Certified Nursing Aide",
            "CNA",
            Specialty(6, "Certified Nursing Aide", "#007AFF", "CNA")
        )
    )

    private val shiftsPayload =
        ShiftsPayload(listOf(DatePayload("2022-05-01", listOf(shift1, shift2))))


    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var api: ShiftsApi

    @Mock
    private lateinit var navBus: NavBus

    @Mock
    private lateinit var dateManager: DateManager

    @Before
    fun beforeTests() {
        MockitoAnnotations.initMocks(this)
        runBlocking {
            `when`(api.getShiftsForWeekStarting("week", null, null, "Dallas, TX")).thenReturn(
                shiftsPayload
            )
            `when`(dateManager.getIsoWeekForWeek(1)).thenReturn("2022-05-09")
            `when`(repository.shifts).thenReturn(MutableLiveData(listOf(shift1, shift2)))
        }
    }

    @Test
    fun `test viewModel get shifts`() {
        val repo = Repository(api, dateManager, CoroutineScope(Dispatchers.IO))
        listViewModel = ShiftListViewModel(repo, navBus)
        runBlocking {
            verify(api, times(1)).getShiftsForWeekStarting("week", null, null, "Dallas, TX")
            listViewModel.fetchAdditionalShifts()
            verify(api, times(1)).getShiftsForWeekStarting("week", "2022-05-09", null, "Dallas, TX")
        }
    }

    @Test
    fun `test viewModel navigates correctly`() {
        listViewModel = ShiftListViewModel(repository, navBus)
        listViewModel.onClick(shift1)
        verify(navBus, times(1)).navigateToDetailFragment(shift1.shiftId)
    }

    @Test
    fun `test detail gets shift`() {
        val repo = Repository(api, dateManager, CoroutineScope(Dispatchers.IO))
        detailViewModel = ShiftDetailViewModel(repo, 2)
        detailViewModel.shift.observeForever { // trigger observer }
            runBlocking { delay(2000) }
            Assert.assertEquals(shift2, detailViewModel.shift.value)

        }
    }
}