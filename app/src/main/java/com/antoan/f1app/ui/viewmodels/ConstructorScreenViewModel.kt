package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorInfo
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.repositories.RacesRepository
import com.antoan.f1app.api.repositories.ConstructorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstructorScreenViewModel @Inject constructor(
    private val repository: ConstructorsRepository,
    private val racesRepository: RacesRepository,
    apiSingleton: ApiSingleton

) : ViewModel() {

    private val _constructorInfo = MutableStateFlow<ConstructorInfo>(ConstructorInfo())
    val constructorInfo: StateFlow<ConstructorInfo> = _constructorInfo

    private val _schedule = MutableStateFlow<Schedule>(Schedule(0))
    val schedule: StateFlow<Schedule> = _schedule

    val baseUrl: String = apiSingleton.getBaseUrl()

    fun loadConstructorInfo(constructorId: String) {
        viewModelScope.launch {
            _constructorInfo.value = repository.getConstructor(constructorId)
            _schedule.value = racesRepository.getSchedule()
        }
    }

}