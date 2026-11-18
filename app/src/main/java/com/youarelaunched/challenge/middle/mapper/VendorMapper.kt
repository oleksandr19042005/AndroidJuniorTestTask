package com.youarelaunched.challenge.middle.mapper

import com.youarelaunched.challenge.middle.domain.Category
import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.network.models.NetworkVendor

fun NetworkVendor.toDomain(): Vendor = Vendor(
    id = id,
    companyName = companyName,
    coverPhotoUrl = coverPhoto.mediaUrl,
    areaServed = areaServed,
    isFavorite = favorite,
    categories = categories?.map { networkCategory ->
        Category(
            id = networkCategory.id,
            name = networkCategory.name,
            imageUrl = networkCategory.image.mediaUrl
        )
    } ?: emptyList(),
    tags = tags?.map { it.name } ?: emptyList()
)
