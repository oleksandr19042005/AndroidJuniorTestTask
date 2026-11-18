package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.SearchBar
import com.youarelaunched.challenge.ui.screen.view.components.VendorCard
import com.youarelaunched.challenge.ui.screen.viewmodel.VendorsVM
import com.youarelaunched.challenge.ui.theme.VendorSubtitleColor

@Composable
fun VendorsScreen(viewModel: VendorsVM) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.vendors_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        VendorsContent(
            uiState = uiState,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun VendorsContent(
    uiState: VendorsScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Search Bar
        SearchBar(
            query = uiState.searchQuery,
            onQueryChanged = onSearchQueryChanged,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary
                    )
                }
            }

            uiState.hasNoResults -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_vendors_found),
                        fontSize = 16.sp,
                        color = VendorSubtitleColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = uiState.vendors,
                        key = { it.id }
                    ) { vendor ->
                        VendorCard(vendor = vendor)
                    }
                }
            }
        }
    }
}
