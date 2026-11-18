package com.youarelaunched.challenge.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class VendorsVM(
    private val repository: VendorRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    val searchQuery: StateFlow<String> = query.asStateFlow()

    private val loadResult = MutableStateFlow<LoadResult>(LoadResult.Loading)

    init {
        loadVendors()
    }

    /**
     * The screen state is derived from two sources: the loaded data and the
     * (debounced) search query. No new coroutine is launched per keystroke.
     */
    val uiState: StateFlow<VendorsScreenUiState> = combine(
        loadResult,
        query.debounce { typed -> if (typed.isEmpty()) 0L else SEARCH_DEBOUNCE_MS }
    ) { result, searchText ->
        when (result) {
            LoadResult.Loading -> VendorsScreenUiState.Loading
            is LoadResult.Error -> VendorsScreenUiState.Error
            is LoadResult.Success -> {
                val filtered = repository.filter(result.vendors, searchText)
                if (filtered.isEmpty()) {
                    VendorsScreenUiState.Empty
                } else {
                    VendorsScreenUiState.Content(filtered)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
        initialValue = VendorsScreenUiState.Loading
    )

    fun onSearchQueryChanged(value: String) {
        query.value = value
    }

    fun retry() {
        loadVendors()
    }

    private fun loadVendors() {
        viewModelScope.launch {
            loadResult.value = LoadResult.Loading
            loadResult.value = try {
                LoadResult.Success(repository.getVendors())
            } catch (e: Exception) {
                LoadResult.Error
            }
        }
    }

    private sealed interface LoadResult {
        object Loading : LoadResult
        data class Success(val vendors: List<Vendor>) : LoadResult
        object Error : LoadResult
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 300L
        const val STOP_TIMEOUT_MS = 5_000L
    }
}
