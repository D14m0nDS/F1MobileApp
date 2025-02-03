package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.repositories.RacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceScreenViewModel @Inject constructor(
    private val repository: RacesRepository
) : ViewModel() {
    private val _race = MutableStateFlow<Race>(Race())
    val race: StateFlow<Race> = _race

    fun loadRaceInfo(season: String, round: Int) {
        viewModelScope.launch {
            _race.value = repository.getRace(season, round)
        }
    }
}