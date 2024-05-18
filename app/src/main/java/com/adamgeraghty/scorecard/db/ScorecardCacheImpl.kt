package com.adamgeraghty.scorecard.db

import com.adamgeraghty.scorecard.utils.SqlConverter.toDataList
import javax.inject.Inject

class ScorecardCacheImpl
    @Inject
    constructor(
        database: Database,
    ) : ScorecardCache {
        private val queries: ScorecardDBQueries = database.scorecardDBQueries

        override fun insertData(data: DataList) {
            queries.insertData(
                id = data.id.toLong(),
                name = data.name,
            )
        }

        override fun insertData(dataList: List<DataList>) {
            for (data in dataList) {
                insertData(data)
            }
        }

        override fun getData(): List<DataList> {
            return queries.getData().executeAsList().toDataList()
        }

        override fun deleteData() {
            return queries.transaction {
                queries.deleteData()
            }
        }
    }
