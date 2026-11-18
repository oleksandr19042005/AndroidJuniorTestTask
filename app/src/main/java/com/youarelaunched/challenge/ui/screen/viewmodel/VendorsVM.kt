package com.youarelaunched.challenge.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VendorsVM(
    private val repository: VendorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VendorsScreenUiState(isLoading = true))
    val uiState: StateFlow<VendorsScreenUiState> = _uiState.asStateFlow()

    init {
        loadVendors()
    }

    private fun loadVendors() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val vendors = repository.getVendors()
                _uiState.update {
                    it.copy(
                        vendors = vendors,
                        isLoading = false,
                        hasNoResults = vendors.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        vendors = emptyList(),
                        isLoading = false,
                        hasNoResults = true
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchVendors(query)
    }

    private fun searchVendors(query: String) {
        viewModelScope.launch {
            try {
                val vendors = repository.searchVendors(query)
                _uiState.update {
                    it.copy(
                        vendors = vendors,
                        hasNoResults = vendors.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        vendors = emptyList(),
                        hasNoResults = true
                    )
                }
            }
        }
    }
}
