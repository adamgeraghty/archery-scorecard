package com.adamgeraghty.scorecard.cache

import com.adamgeraghty.scorecard.db.Database

class ScorecardDatabaseFactory(private val driverFactory: DriverFactory) {
    fun createDriver(): Database {
        return Database(
            driver = driverFactory.createDriver(),
        )
    }
}
