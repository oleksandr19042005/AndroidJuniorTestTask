package com.youarelaunched.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.screen.viewmodel.VendorsVM
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: VendorsVM by viewModels {
        viewModelFactory {
            initializer {
                val container = (application as VendorApp).container
                VendorsVM(container.vendorRepository)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VendorAppTheme {
                VendorsScreen(viewModel = viewModel)
            }
        }
    }
}
