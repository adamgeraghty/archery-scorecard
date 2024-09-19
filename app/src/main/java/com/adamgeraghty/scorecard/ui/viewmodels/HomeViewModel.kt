package com.adamgeraghty.scorecard.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamgeraghty.scorecard.data.repo.ShotsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val shotsRepository: ShotsRepository,
    ) : ViewModel() {
        fun getAllShootDates() {
            viewModelScope.launch {
                val dates = shotsRepository.getAllShootDates()
            }
        }
    }
