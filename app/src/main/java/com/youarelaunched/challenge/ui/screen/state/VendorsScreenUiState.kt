package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.middle.domain.Vendor

/**
 * Exhaustive set of states the vendors screen can be in. Modeling them as a
 * sealed interface makes illegal combinations (e.g. loading + empty) impossible.
 */
sealed interface VendorsScreenUiState {

    object Loading : VendorsScreenUiState

    data class Content(val vendors: List<Vendor>) : VendorsScreenUiState

    object Empty : VendorsScreenUiState

    object Error : VendorsScreenUiState
}
