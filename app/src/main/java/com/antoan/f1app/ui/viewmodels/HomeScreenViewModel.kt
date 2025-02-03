package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.repositories.RacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: RacesRepository,
    private val apiSingleton: ApiSingleton

) : ViewModel() {
    private val _schedule = MutableStateFlow<Schedule>(Schedule(0, emptyList()))
    val schedule: StateFlow<Schedule> = _schedule

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val baseUrl: String = apiSingleton.getBaseUrl()

    init {
        loadSchedule()
    }

    private fun loadSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _schedule.value = repository.getSchedule()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}