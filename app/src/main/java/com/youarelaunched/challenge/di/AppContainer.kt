package com.youarelaunched.challenge.di

import android.content.Context
import com.youarelaunched.challenge.middle.repository.VendorRepository
import com.youarelaunched.challenge.network.api.ApiVendors
import com.youarelaunched.challenge.network.api.NetworkDataProvider
import kotlinx.coroutines.Dispatchers

/**
 * Tiny manual DI container: the single place where the app's dependency graph is
 * assembled. Lives for the whole process and is created by [com.youarelaunched.challenge.VendorApp].
 *
 * For a one-screen app this keeps wiring centralized and testable without pulling
 * in an annotation-processing framework. Swapping in Hilt later only touches this
 * layer and the entry points.
 */
class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    private val apiVendors: ApiVendors = NetworkDataProvider(
        workDispatcher = Dispatchers.IO,
        appContext = appContext
    )

    val vendorRepository: VendorRepository = VendorRepository(apiVendors)
}
