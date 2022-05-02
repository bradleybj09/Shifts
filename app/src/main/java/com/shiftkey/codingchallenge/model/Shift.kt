package com.shiftkey.codingchallenge.model

import android.graphics.Color
import android.util.Log
import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Shift(
    @SerializedName("shift_id") val shiftId: Long,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("normalized_start_date_time") val normalizedStartDateTime: String,
    @SerializedName("normalized_end_date_time") val normalizedEndDateTime: String,
    val timezone: String,
    @SerializedName("premium_rate") val premiumRate: Boolean,
    val covid: Boolean,
    @SerializedName("shift_kind") val shiftKind: String,
    @SerializedName("within_distance") val withinDistance: Int,
    @SerializedName("facility_type") val facilityType: FacilityType,
    val skill: Skill,
    @SerializedName("localized_specialty") val localizedSpecialty: LocalizedSpecialty,
) {
    val displayShiftTime: String
    get() {
        val start = Instant.parse(startTime.convertTimeToISO())
        val end = Instant.parse(endTime.convertTimeToISO())
        val startFormatter = DateTimeFormatter.ofPattern("MMM d, h:mma").withZone(ZoneId.systemDefault())
        val endFormatter = DateTimeFormatter.ofPattern("h:mma").withZone(ZoneId.systemDefault())
        return "${startFormatter.format(start)} - ${endFormatter.format(end)}".replace("AM","am").replace("PM","pm")
    }

    val distance: String
    get() = "$withinDistance mi"
}

private fun String.convertTimeToISO(): String {
    return this.replace("+00:00","z")
}

data class FacilityType(
    val id: Long,
    val name: String,
    val color: String,
)

data class Skill(
    val id: Long,
    val name: String,
    val color: String,
) {
    val badgeColor: Int
    get() {
        return try {
            Color.parseColor(color)
        } catch (e: Exception) {
            Log.e("SkillPayload","failed to parse color $color, exception $e")
            Color.WHITE
        }
    }
}

data class LocalizedSpecialty(
    val id: Long,
    val specialtyId: Long,
    val stateId: Int,
    val name: String,
    val abbreviation: String,
    val specialty: Specialty,
)

data class Specialty(
    val id: Long,
    val name: String,
    val color: String,
    val abbreviation: String,
)

data class DatePayload(
    val date: String,
    val shifts: List<Shift>,
)

data class ShiftsPayload(
    val data: List<DatePayload>,
)