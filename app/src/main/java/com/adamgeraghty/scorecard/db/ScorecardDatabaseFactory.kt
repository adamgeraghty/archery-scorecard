package com.adamgeraghty.scorecard.db

class ScorecardDatabaseFactory(private val driverFactory: DriverFactory) {
    fun createDriver(): Database {
        return Database(
            driver = driverFactory.createDriver(),
        )
    }
}
