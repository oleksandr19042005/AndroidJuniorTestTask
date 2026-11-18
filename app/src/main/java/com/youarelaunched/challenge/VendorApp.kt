package com.youarelaunched.challenge

import android.app.Application
import com.youarelaunched.challenge.di.AppContainer

/**
 * Application entry point that owns the [AppContainer] — the app's composition root.
 */
class VendorApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
