package com.adamgeraghty.scorecard.data.repo

import com.adamgeraghty.scorecard.cache.ScorecardCache
import com.adamgeraghty.scorecard.cache.ShootDates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShotsRepository
    @Inject
    constructor(
        private val scorecardCache: ScorecardCache,
    ) {
        suspend fun getAllShootDates(): List<ShootDates> {
            return withContext(Dispatchers.IO) {
                scorecardCache.getAllShootDates()
            }
        }
    }
