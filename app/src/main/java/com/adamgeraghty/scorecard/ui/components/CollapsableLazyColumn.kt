package com.adamgeraghty.scorecard.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CollapsibleLazyColumn(
    sections: List<CollapsibleSection>,
    modifier: Modifier = Modifier,
    onRowClick: (String) -> Unit,
) {
    Column(modifier) {
        sections.forEach { section ->
            var collapsed by remember { mutableStateOf(true) }

            CollapsibleSectionHeader(
                title = section.title,
                collapsed = collapsed,
                onClick = { collapsed = !collapsed },
            )

            CollapsibleSectionContent(
                section = section,
                collapsed = collapsed,
                onRowClick = onRowClick,
            )
        }
    }
}

@Composable
fun CollapsibleSectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    collapsed: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }.fillMaxWidth(),
    ) {
        CollapsibleIcon(collapsed)
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
        )
    }
}

@Composable
private fun CollapsibleIcon(collapsed: Boolean) {
    val icon = if (collapsed) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp
    Icon(
        imageVector = icon,
        contentDescription = "Collapse/Expand",
        tint = Color.LightGray,
        modifier = Modifier.padding(8.dp),
    )
}

@Composable
fun CollapsibleSectionContent(
    section: CollapsibleSection,
    collapsed: Boolean,
    onRowClick: (String) -> Unit,
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
                        .padding(vertical = 10.dp)
                        .clickable { onRowClick(row) },
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

data class CollapsibleSection(val title: String, val rows: List<String>)
