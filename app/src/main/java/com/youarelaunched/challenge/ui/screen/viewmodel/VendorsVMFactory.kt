package com.youarelaunched.challenge.ui.screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.network.api.NetworkDataProvider
import kotlinx.coroutines.Dispatchers

class VendorsVMFactory(
    private val appContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dataProvider = NetworkDataProvider(
            workDispatcher = Dispatchers.IO,
            appContext = appContext
        )
        val repository = VendorRepository(dataProvider)
        return VendorsVM(repository) as T
    }
}
