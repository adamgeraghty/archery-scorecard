package com.adamgeraghty.scorecard.di

import android.content.Context
import com.adamgeraghty.scorecard.ScorecardApp
import com.adamgeraghty.scorecard.cache.ScorecardCache
import com.adamgeraghty.scorecard.data.repo.ShotsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplication(
        @ApplicationContext app: Context,
    ): ScorecardApp {
        return app as ScorecardApp
    }

    @Provides
    fun provideShotsRepository(scorecardCache: ScorecardCache): ShotsRepository {
        return ShotsRepository(scorecardCache)
    }
}
