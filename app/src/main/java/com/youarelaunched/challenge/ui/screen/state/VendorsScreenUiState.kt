package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.data.domain.Vendor

sealed interface VendorsScreenUiState {

    object Loading : VendorsScreenUiState

    data class Content(val vendors: List<Vendor>) : VendorsScreenUiState

    object Empty : VendorsScreenUiState

    object Error : VendorsScreenUiState
}
