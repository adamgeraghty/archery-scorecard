package com.adamgeraghty.scorecard.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CollapsibleLazyColumn(
    sections: List<CollapsibleSection>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        sections.forEach { section ->
            var collapsed by remember { mutableStateOf(section.collapsed) }

            CollapsibleSectionHeader(section.title, collapsed) {
                collapsed = !collapsed
                section.collapsed = collapsed
            }

            CollapsibleSectionContent(section, collapsed)
        }
    }
}

@Composable
fun CollapsibleSectionHeader(
    title: String,
    collapsed: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth(),
    ) {
        Icon(
            imageVector = if (collapsed) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = "Collapse/Expand",
            tint = Color.LightGray,
            modifier = Modifier.padding(8.dp),
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
        )
    }
}

@Composable
fun CollapsibleSectionContent(
    section: CollapsibleSection,
    collapsed: Boolean,
) {
    AnimatedVisibility(
        visible = !collapsed,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Column(
            modifier = Modifier.animateContentSize(),
        ) {
            section.rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                ) {
                    Text(
                        text = row,
                        modifier = Modifier.weight(1f),
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

data class CollapsibleSection(val title: String, val rows: List<String>, var collapsed: Boolean = true)
