package com.adamgeraghty.scorecard.di

import com.adamgeraghty.scorecard.ScorecardApp
import com.adamgeraghty.scorecard.cache.DriverFactory
import com.adamgeraghty.scorecard.cache.ScorecardCache
import com.adamgeraghty.scorecard.cache.ScorecardCacheImpl
import com.adamgeraghty.scorecard.cache.ScorecardDatabaseFactory
import com.adamgeraghty.scorecard.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {
    @Provides
    @Singleton
    fun provideScorecardDatabaseFactoryDriver(context: ScorecardApp): Database {
        return ScorecardDatabaseFactory(
            driverFactory = DriverFactory(context = context),
        ).createDriver()
    }

    @Provides
    @Singleton
    fun provideScorecardCache(database: Database): ScorecardCache {
        return ScorecardCacheImpl(
            database = database,
        )
    }
}
