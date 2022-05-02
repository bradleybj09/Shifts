package com.shiftkey.codingchallenge.util

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateManager @Inject constructor() {

    private val currentTime: Instant
    get() = Instant.now()

    fun getIsoWeekForWeek(weekIndexed: Int): String {
        val duration = Duration.ofDays(weekIndexed * 7L)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
        return formatter.format(currentTime.plus(duration))
    }
}