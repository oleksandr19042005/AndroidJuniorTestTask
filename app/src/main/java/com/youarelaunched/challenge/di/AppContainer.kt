package com.youarelaunched.challenge.di

import android.content.Context
import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.network.api.ApiVendors
import com.youarelaunched.challenge.network.api.NetworkDataProvider
import kotlinx.coroutines.Dispatchers
class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    private val apiVendors: ApiVendors = NetworkDataProvider(
        workDispatcher = Dispatchers.IO,
        appContext = appContext
    )

    val vendorRepository: VendorRepository = VendorRepository(apiVendors)
}
