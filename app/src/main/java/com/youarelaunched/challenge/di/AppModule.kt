package com.youarelaunched.challenge.di

import android.content.Context
import com.youarelaunched.challenge.data.repository.VendorRepository
import com.youarelaunched.challenge.network.api.ApiVendors
import com.youarelaunched.challenge.network.api.NetworkDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideApiVendors(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ApiVendors = NetworkDataProvider(
        workDispatcher = ioDispatcher,
        appContext = context
    )

    @Provides
    @Singleton
    fun provideVendorRepository(apiVendors: ApiVendors): VendorRepository =
        VendorRepository(apiVendors)
}
