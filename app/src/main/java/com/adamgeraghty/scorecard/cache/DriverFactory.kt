package com.adamgeraghty.scorecard.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.adamgeraghty.scorecard.db.Database

class DriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "scorecard.db")
    }
}
