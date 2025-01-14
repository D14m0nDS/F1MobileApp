package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.RaceInfo
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
    private val _raceInfo = MutableStateFlow<RaceInfo>(RaceInfo(""))
    val raceInfo: StateFlow<RaceInfo> = _raceInfo

    init {
        loadRaceInfo()
    }

    private fun loadRaceInfo() {
        viewModelScope.launch {
            _raceInfo.value = repository.getRaceInfo()
        }
    }
}