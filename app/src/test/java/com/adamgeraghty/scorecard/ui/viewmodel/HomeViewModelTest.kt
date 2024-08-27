package com.adamgeraghty.scorecard.ui.viewmodel

import com.adamgeraghty.scorecard.rules.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `loadSections populates sections correctly`() =
        runTest {
            // Given
            val expectedSections = listOf(
                CollapsibleSectionData(
                    title = "May",
                    rows = listOf("Score 1", "Score 2", "Score 3"),
                ),
                CollapsibleSectionData(
                    title = "April",
                    rows = listOf("Score 4", "Score 5", "Score 6"),
                ),
                CollapsibleSectionData(
                    title = "March",
                    rows = listOf("Score 7", "Score 8", "Score 9"),
                ),
            )

            // When
            viewModel.loadSections()

            // Then
            val actualSections = viewModel.sections.value
            assertEquals(expectedSections, actualSections)
        }
}
