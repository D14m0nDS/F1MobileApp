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
class HomeScreenViewModel @Inject constructor(
    private val repository: RacesRepository

) : ViewModel() {
    private val _races = MutableStateFlow<List<Race>>(emptyList())
    val races: StateFlow<List<Race>> = _races

    init {
        loadRaces()
    }

    private fun loadRaces() {
        viewModelScope.launch {
            _races.value = repository.getAllRaces()
        }
    }
}