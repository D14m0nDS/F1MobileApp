package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

) : ViewModel() {

    private val _driverInfo = MutableStateFlow<Driver>(Driver())

    val driverInfo: StateFlow<Driver> = _driverInfo

    fun loadDriverInfo(driverId: String) {
        viewModelScope.launch {
            _driverInfo.value = repository.getDriver(driverId)
        }
    }
}