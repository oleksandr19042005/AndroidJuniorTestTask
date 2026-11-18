package com.youarelaunched.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.screen.viewmodel.VendorsVM
import com.youarelaunched.challenge.ui.screen.viewmodel.VendorsVMFactory
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: VendorsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = VendorsVMFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[VendorsVM::class.java]

        setContent {
            VendorAppTheme {
                VendorsScreen(viewModel = viewModel)
            }
        }
    }
}
