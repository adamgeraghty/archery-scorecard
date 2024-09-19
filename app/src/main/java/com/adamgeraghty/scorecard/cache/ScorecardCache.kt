package com.adamgeraghty.scorecard.cache

interface ScorecardCache {
    fun insertShootDate(data: ShootDates)

    fun insertShootDate(shootDates: List<ShootDates>)

    fun getAllShootDates(): List<ShootDates>

    fun deleteAllShootDates()
}

class ShootDates(
    val id: Int,
    val name: String,
    val date: Long,
)
