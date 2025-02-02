package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.Circuit
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.repositories.RacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceInfoViewModel @Inject constructor(
    private val repository: RacesRepository
) : ViewModel() {
    private val _race = MutableStateFlow<Race>(Race())
    val race: StateFlow<Race> = _race

    init {
        loadRaceInfo()
    }

    private fun loadRaceInfo() {
        viewModelScope.launch {
            _race.value = repository.getRace("2024", 1)
        }
    }
}