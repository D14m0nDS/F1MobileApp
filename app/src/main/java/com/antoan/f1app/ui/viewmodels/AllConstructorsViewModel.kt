package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.repositories.ConstructorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllConstructorsViewModel @Inject constructor(
    private val repository: ConstructorsRepository
) : ViewModel() {

    private val _constructors = MutableStateFlow<List<Constructor>>(emptyList())
    val constructors: StateFlow<List<Constructor>> = _constructors

    init {
        loadConstructors()
    }

    private fun loadConstructors() {
        viewModelScope.launch {
            _constructors.value = repository.getAllConstructors()
        }
    }
}
