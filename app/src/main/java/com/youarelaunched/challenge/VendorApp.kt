package com.youarelaunched.challenge

import android.app.Application
import com.youarelaunched.challenge.di.AppContainer

class VendorApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
