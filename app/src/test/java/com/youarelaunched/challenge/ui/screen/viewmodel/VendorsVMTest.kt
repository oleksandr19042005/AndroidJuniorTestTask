package com.youarelaunched.challenge.ui.screen.viewmodel

import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.testutil.FailingApiVendors
import com.youarelaunched.challenge.testutil.FakeApiVendors
import com.youarelaunched.challenge.testutil.MainDispatcherRule
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VendorsVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun viewModel(api: com.youarelaunched.challenge.network.api.ApiVendors) =
        VendorsVM(VendorRepository(api))

    @Test
    fun `emits content with all vendors once loaded`() = runTest(mainDispatcherRule.dispatcher) {
        val vm = viewModel(FakeApiVendors())
        backgroundScope.launch { vm.uiState.collect {} }

        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is VendorsScreenUiState.Content)
        assertEquals(2, (state as VendorsScreenUiState.Content).vendors.size)
    }

    @Test
    fun `search query filters the content`() = runTest(mainDispatcherRule.dispatcher) {
        val vm = viewModel(FakeApiVendors())
        backgroundScope.launch { vm.uiState.collect {} }
        advanceUntilIdle()

        vm.onSearchQueryChanged("florist")
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is VendorsScreenUiState.Content)
        assertEquals(1, (state as VendorsScreenUiState.Content).vendors.size)
        assertEquals("Florists + Fashion", state.vendors.first().companyName)
    }

    @Test
    fun `search with no matches emits empty`() = runTest(mainDispatcherRule.dispatcher) {
        val vm = viewModel(FakeApiVendors())
        backgroundScope.launch { vm.uiState.collect {} }
        advanceUntilIdle()

        vm.onSearchQueryChanged("no-match")
        advanceUntilIdle()

        assertEquals(VendorsScreenUiState.Empty, vm.uiState.value)
    }

    @Test
    fun `emits error when loading fails`() = runTest(mainDispatcherRule.dispatcher) {
        val vm = viewModel(FailingApiVendors())
        backgroundScope.launch { vm.uiState.collect {} }

        advanceUntilIdle()

        assertEquals(VendorsScreenUiState.Error, vm.uiState.value)
    }
}
