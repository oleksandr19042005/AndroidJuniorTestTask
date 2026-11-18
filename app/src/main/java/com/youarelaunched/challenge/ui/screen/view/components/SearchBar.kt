package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.ui.theme.SearchBarBackground
import com.youarelaunched.challenge.ui.theme.SearchBarHintColor
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import com.youarelaunched.challenge.ui.theme.VendorTitleColor

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(color = SearchBarBackground, shape = RoundedCornerShape(16.dp))
            .height(48.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (query.isEmpty()) {
            Text(
                text = stringResource(R.string.search_hint),
                style = MaterialTheme.typography.body1,
                color = SearchBarHintColor
            )
        }

        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(color = VendorTitleColor),
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    VendorAppTheme {
        SearchBar(
            query = "",
            onQueryChanged = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
