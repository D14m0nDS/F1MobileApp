package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorStandings
import com.antoan.f1app.api.models.DriverStandings
import com.antoan.f1app.api.repositories.StandingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val repository: StandingsRepository,
    apiSingleton: ApiSingleton
) : ViewModel() {

    private val _driverStandings = MutableStateFlow<List<DriverStandings>>(emptyList())
    private val _constructorStandings = MutableStateFlow<List<ConstructorStandings>>(emptyList())

    val driverStandings: StateFlow<List<DriverStandings>> = _driverStandings
    val constructorStandings: StateFlow<List<ConstructorStandings>> = _constructorStandings

    val baseUrl: String = apiSingleton.getBaseUrl()


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