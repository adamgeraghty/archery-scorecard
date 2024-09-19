package com.adamgeraghty.scorecard.utils

import com.adamgeraghty.scorecard.cache.ShootDates
import com.adamgeraghty.scorecard.db.Shoot_Dates

object SqlConverter {
    fun List<Shoot_Dates>.toDataList(): List<ShootDates> {
        return map { it.toData() }
    }

    private fun Shoot_Dates.toData(): ShootDates {
        return ShootDates(
            id = id.toInt(),
            name = name,
            date = shoot_date,
        )
    }
}
