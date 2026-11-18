package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.data.domain.Category
import com.youarelaunched.challenge.data.domain.Vendor
import com.youarelaunched.challenge.ui.theme.SaveActionColor
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import com.youarelaunched.challenge.ui.theme.VendorCardBackground
import com.youarelaunched.challenge.ui.theme.VendorSubtitleColor
import com.youarelaunched.challenge.ui.theme.VendorTitleColor

@Composable
fun VendorCard(
    vendor: Vendor,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = VendorCardBackground,
        elevation = 2.dp
    ) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(vendor.coverPhotoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = vendor.companyName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                SaveBadge(
                    isSaved = vendor.isFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = vendor.areaServed,
                        style = MaterialTheme.typography.body2,
                        color = VendorTitleColor
                    )
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = vendor.companyName,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = VendorTitleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (vendor.categories.isNotEmpty()) {
                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        vendor.categories.chunked(2).forEach { rowCategories ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                rowCategories.forEach { category ->
                                    CategoryItem(
                                        name = category.name,
                                        imageUrl = category.imageUrl,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowCategories.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                if (vendor.tags.isNotEmpty()) {
                    Text(
                        text = vendor.tags.joinToString(separator = " ") { "• $it" },
                        style = MaterialTheme.typography.caption,
                        color = VendorSubtitleColor,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun SaveBadge(
    isSaved: Boolean,
    modifier: Modifier = Modifier
) {
    val circleColor = if (isSaved) SaveActionColor else Color.White
    val iconTint = if (isSaved) Color.White else SaveActionColor
    val icon = if (isSaved) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
    val contentDescription = if (isSaved) {
        stringResource(R.string.cd_saved_vendor)
    } else {
        stringResource(R.string.cd_save_vendor)
    }

    Box(
        modifier = modifier
            .size(36.dp)
            .background(color = circleColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun CategoryItem(
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build(),
            contentDescription = name,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            color = VendorTitleColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VendorCardPreview() {
    VendorAppTheme {
        VendorCard(
            vendor = Vendor(
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
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
