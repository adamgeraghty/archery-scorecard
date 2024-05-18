package com.adamgeraghty.scorecard.db

interface ScorecardCache {
    fun insertData(data: DataList)

    fun insertData(dataList: List<DataList>)

    fun getData(): List<DataList>

    fun deleteData()
}

class DataList(
    val id: Int,
    val name: String,
)
