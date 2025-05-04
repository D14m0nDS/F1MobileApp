package com.antoan.f1app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.repositories.ConstructorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstructorScreenViewModel @Inject constructor(
    private val repository: ConstructorsRepository,
    apiSingleton: ApiSingleton

) : ViewModel() {

    private val _constructorInfo = MutableStateFlow<Constructor>(Constructor())

    val constructorInfo: StateFlow<Constructor> = _constructorInfo

    val baseUrl: String = apiSingleton.getBaseUrl()

    fun loadConstructorInfo(constructorId: String) {
        viewModelScope.launch {
            _constructorInfo.value = repository.getConstructor(constructorId)
        }
    }

}