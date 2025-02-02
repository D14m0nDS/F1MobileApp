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
class AllDriversViewModel @Inject constructor(
    private val repository: DriversRepository
) : ViewModel() {

    private val _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            _drivers.value = repository.getAllDrivers()
        }
    }
}