package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.ConstructorStanding
import com.antoan.f1app.api.models.DriverStanding
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

    private val _driverStandings = MutableStateFlow<List<DriverStanding>>(emptyList())
    private val _constructorStandings = MutableStateFlow<List<ConstructorStanding>>(emptyList())

    val driverStandings: StateFlow<List<DriverStanding>> = _driverStandings
    val constructorStandings: StateFlow<List<ConstructorStanding>> = _constructorStandings

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