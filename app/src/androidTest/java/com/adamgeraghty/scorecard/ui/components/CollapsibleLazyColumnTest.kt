package com.adamgeraghty.scorecard.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.adamgeraghty.scorecard.ui.theme.ScorecardTheme
import org.junit.Rule
import org.junit.Test

class CollapsibleLazyColumnTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun collapsibleLazyColumn_displaysSectionsAndRows() {
        composeTestRule.setContent {
            ScorecardTheme {
                CollapsibleLazyColumn(
                    sections = listOf(
                        CollapsibleSection(
                            title = "May",
                            rows = listOf("Score 1", "Score 2", "Score 3"),
                        ),
                        CollapsibleSection(
                            title = "April",
                            rows = listOf("Score 1", "Score 2", "Score 3"),
                        ),
                        CollapsibleSection(
                            title = "March",
                            rows = listOf("Score 1", "Score 2", "Score 3"),
                        ),
                    ),
                    onRowClick = {}
                )
            }
        }

        // Verify that the section titles are displayed
        composeTestRule.onNodeWithText("May").assertIsDisplayed()
        composeTestRule.onNodeWithText("April").assertIsDisplayed()
        composeTestRule.onNodeWithText("March").assertIsDisplayed()

        // Verify that the rows are displayed
        composeTestRule.onNodeWithText("Score 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Score 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Score 3").assertIsDisplayed()
    }
}
