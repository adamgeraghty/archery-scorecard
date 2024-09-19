package com.adamgeraghty.scorecard.cache

import com.adamgeraghty.scorecard.db.Database
import com.adamgeraghty.scorecard.db.ScorecardDBQueries
import com.adamgeraghty.scorecard.utils.SqlConverter.toDataList
import javax.inject.Inject

class ScorecardCacheImpl
    @Inject
    constructor(
        database: Database,
    ) : ScorecardCache {
        private val queries: ScorecardDBQueries = database.scorecardDBQueries

        override fun insertShootDate(data: ShootDates) {
            queries.insertData(
                id = data.id.toLong(),
                name = data.name,
                shoot_date = data.date,
            )
        }

        override fun insertShootDate(shootDates: List<ShootDates>) {
            for (data in shootDates) {
                insertShootDate(data)
            }
        }

        override fun getAllShootDates(): List<ShootDates> {
            return queries.getData().executeAsList().toDataList()
        }

        override fun deleteAllShootDates() {
            return queries.transaction {
                queries.deleteData()
            }
        }
    }
