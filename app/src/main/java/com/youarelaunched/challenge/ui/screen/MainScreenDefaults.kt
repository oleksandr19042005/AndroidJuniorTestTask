package com.youarelaunched.challenge.ui.screen

/**
 * Tuning values for the vendors (main) screen, kept in one place.
 */
object MainScreenDefaults {

    /** Debounce applied to the search query before the list is re-filtered. */
    const val SEARCH_DEBOUNCE_MS = 300L

    /** How long uiState keeps its upstream alive after the last collector leaves. */
    const val STOP_TIMEOUT_MS = 5_000L
}
