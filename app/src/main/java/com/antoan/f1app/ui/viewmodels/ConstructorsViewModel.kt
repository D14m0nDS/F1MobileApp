package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.repositories.ConstructorsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllConstructorsViewModel(private val repository: ConstructorsRepository) : ViewModel() {
    private val _constructors = MutableStateFlow<List<String>>(emptyList())
    val constructors: StateFlow<List<String>> = _constructors

    init {
        loadConstructors()
    }

    private fun loadConstructors() {
        viewModelScope.launch {
            _constructors.value = repository.getAllConstructors()
        }
    }
}
