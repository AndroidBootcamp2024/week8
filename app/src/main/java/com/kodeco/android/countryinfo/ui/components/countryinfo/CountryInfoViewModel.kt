package com.kodeco.android.countryinfo.ui.components.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryInfoViewModel(val repository: CountryRepository?) : ViewModel() {
    init {
        loadCountries()
        viewModelScope.launch {
            while (true) {
                delay(1_000L)
                increment()
            }
        }
    }
    private val _counterFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _tapFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _backFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    val counterFlow: StateFlow<Int> = _counterFlow.asStateFlow()
    val tapFlow: StateFlow<Int> = _tapFlow.asStateFlow()
    val backFlow: StateFlow<Int> = _backFlow.asStateFlow()
    private val _uiState: MutableStateFlow<CountryInfoState> = MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    class CountryInfoViewModelFactory(private val repository: CountryRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryInfoViewModel(repository) as T
    }

    fun setLoadingState() {
        _uiState.value = CountryInfoState.Loading
        loadCountries()
    }

    fun tap() {
        _tapFlow.value += 1
    }

    fun tapBack() {
        _backFlow.value += 1
    }

    private fun increment() {
        _counterFlow.value += 1
    }



  private fun loadCountries() {
        viewModelScope.launch {
            delay(1_000)
            repository?.fetchCountries()?.collect {
                if (it.isNotEmpty()) {
                    _uiState.value = CountryInfoState.Success(it)
                } else {
                    _uiState.value = CountryInfoState.Error(Throwable("Request failed: Can't get the countries"))
                }
            }
        }
    }

}