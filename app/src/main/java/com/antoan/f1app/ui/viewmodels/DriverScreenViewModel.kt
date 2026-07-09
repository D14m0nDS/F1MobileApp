package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.repositories.DriversRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverScreenViewModel @Inject constructor(
    private val repository: DriversRepository,
    private val racesRepository: com.antoan.f1app.api.repositories.RacesRepository,
    private val apiSingleton: com.antoan.f1app.api.ApiSingleton

) : ViewModel() {

    private val _driverInfo = MutableStateFlow<Driver>(Driver())
    val driverInfo: StateFlow<Driver> = _driverInfo

    private val _schedule = MutableStateFlow<com.antoan.f1app.api.models.Schedule>(com.antoan.f1app.api.models.Schedule(0))
    val schedule: StateFlow<com.antoan.f1app.api.models.Schedule> = _schedule

    val baseUrl: String = apiSingleton.getBaseUrl()

    fun loadDriverInfo(driverId: String) {
        viewModelScope.launch {
            // fetch driver info
            _driverInfo.value = repository.getDriver(driverId)
            // fetch schedule to resolve race names and circuits
            _schedule.value = racesRepository.getSchedule()
        }
    }
}