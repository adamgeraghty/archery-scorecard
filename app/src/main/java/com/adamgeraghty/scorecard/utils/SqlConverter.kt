package com.adamgeraghty.scorecard.utils

import com.adamgeraghty.scorecard.cache.DataList
import com.adamgeraghty.scorecard.db.Data_Entity

object SqlConverter {
    fun List<Data_Entity>.toDataList(): List<DataList> {
        return map { it.toData() }
    }

    private fun Data_Entity.toData(): DataList {
        return DataList(
            id = id.toInt(),
            name = name,
        )
    }
}
