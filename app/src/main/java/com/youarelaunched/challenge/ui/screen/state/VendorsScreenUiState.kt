package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.middle.domain.Vendor

data class VendorsScreenUiState(
    val vendors: List<Vendor> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val hasNoResults: Boolean = false
)
