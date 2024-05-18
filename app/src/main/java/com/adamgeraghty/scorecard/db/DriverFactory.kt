package com.adamgeraghty.scorecard.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class DriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "scorecard.db")
    }
}
