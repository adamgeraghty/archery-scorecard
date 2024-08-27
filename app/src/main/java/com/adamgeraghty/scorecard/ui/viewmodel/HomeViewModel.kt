package com.adamgeraghty.scorecard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollapsibleSectionData(
    val title: String,
    val rows: List<String>
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _sections = MutableStateFlow<List<CollapsibleSectionData>>(emptyList())
    val sections: StateFlow<List<CollapsibleSectionData>> = _sections

    init {
        loadSections()
    }

    private fun loadSections() {
        viewModelScope.launch {
            // Simulate loading data
            _sections.value = listOf(
                CollapsibleSectionData(
                    title = "May",
                    rows = listOf("Score 1", "Score 2", "Score 3")
                ),
                CollapsibleSectionData(
                    title = "April",
                    rows = listOf("Score 4", "Score 5", "Score 6")
                ),
                CollapsibleSectionData(
                    title = "March",
                    rows = listOf("Score 7", "Score 8", "Score 9")
                )
            )
        }
    }
}
