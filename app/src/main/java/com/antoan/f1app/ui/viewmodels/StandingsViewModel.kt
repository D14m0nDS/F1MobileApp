package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.repositories.StandingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val repository: StandingsRepository
) : ViewModel() {

    private val _driverStandings = MutableStateFlow<List<Driver>>(emptyList())
    private val _constructorStandings = MutableStateFlow<List<Constructor>>(emptyList())

    val driverStandings: StateFlow<List<Driver>> = _driverStandings
    val constructorStandings: StateFlow<List<Constructor>> = _constructorStandings

    init {
        loadStandings()
    }

    private fun loadStandings() {
        viewModelScope.launch {
            _driverStandings.value = repository.getDriverStandings()
            _constructorStandings.value = repository.getConstructorStandings()
        }
    }
}