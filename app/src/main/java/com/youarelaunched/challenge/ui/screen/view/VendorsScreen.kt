package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.middle.domain.Category
import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.view.components.SearchBar
import com.youarelaunched.challenge.ui.screen.view.components.VendorCard
import com.youarelaunched.challenge.ui.screen.viewmodel.VendorsVM
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import com.youarelaunched.challenge.ui.theme.VendorSubtitleColor

@Composable
fun VendorsScreen(viewModel: VendorsVM) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    VendorsContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onRetry = viewModel::retry
    )
}

@Composable
private fun VendorsContent(
    uiState: VendorsScreenUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.vendors_title),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChanged = onSearchQueryChanged,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            when (uiState) {
                VendorsScreenUiState.Loading -> CenteredBox {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }

                VendorsScreenUiState.Empty -> CenteredBox {
                    Message(text = stringResource(R.string.no_vendors_found))
                }

                VendorsScreenUiState.Error -> CenteredBox {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Message(text = stringResource(R.string.error_loading_vendors))
                        Button(onClick = onRetry) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }

                is VendorsScreenUiState.Content -> LazyColumn(
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

@Composable
private fun CenteredBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun Message(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = VendorSubtitleColor,
        textAlign = TextAlign.Center
    )
}

/* ----------------------------- Previews ----------------------------- */

private val previewVendor = Vendor(
    id = 1,
    companyName = "Bill & Jane Grocery",
    coverPhotoUrl = "",
    areaServed = "Birmingham",
    isFavorite = true,
    categories = listOf(
        Category(1, "Green Grocers", ""),
        Category(2, "Cafe & Restaurant", ""),
        Category(3, "Homeware", ""),
        Category(4, "Health & Wellbeing", "")
    ),
    tags = listOf("Best vegetables in town", "Discounts for customers")
)

@Preview(showBackground = true, name = "Content")
@Composable
private fun VendorsContentPreview() {
    VendorAppTheme {
        VendorsContent(
            uiState = VendorsScreenUiState.Content(listOf(previewVendor, previewVendor.copy(id = 2))),
            searchQuery = "",
            onSearchQueryChanged = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun VendorsEmptyPreview() {
    VendorAppTheme {
        VendorsContent(
            uiState = VendorsScreenUiState.Empty,
            searchQuery = "xyz",
            onSearchQueryChanged = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun VendorsErrorPreview() {
    VendorAppTheme {
        VendorsContent(
            uiState = VendorsScreenUiState.Error,
            searchQuery = "",
            onSearchQueryChanged = {},
            onRetry = {}
        )
    }
}
