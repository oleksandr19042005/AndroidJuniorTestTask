package com.youarelaunched.challenge.testutil

import com.youarelaunched.challenge.middle.domain.Category
import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.network.api.ApiVendors
import com.youarelaunched.challenge.network.models.NetworkCategory
import com.youarelaunched.challenge.network.models.NetworkMediaAttachment
import com.youarelaunched.challenge.network.models.NetworkTag
import com.youarelaunched.challenge.network.models.NetworkVendor

object TestData {

    fun media(url: String = "https://example.com/img"): NetworkMediaAttachment =
        NetworkMediaAttachment(id = 1, mediaType = "image", mediaUrl = url)

    fun networkVendor(
        id: Int,
        name: String,
        area: String = "Area",
        favorite: Boolean = false,
        categories: List<NetworkCategory>? = emptyList(),
        tags: List<NetworkTag>? = emptyList()
    ): NetworkVendor = NetworkVendor(
        areaServed = area,
        categories = categories,
        companyName = name,
        coverPhoto = media(),
        profilePhoto = null,
        distance = null,
        favorite = favorite,
        id = id,
        shopType = "shop",
        tags = tags,
        chatId = null,
        type = null
    )

    val networkVendors: List<NetworkVendor> = listOf(
        networkVendor(
            id = 1,
            name = "Florists + Fashion",
            categories = listOf(NetworkCategory(10, media(), "Florists")),
            tags = listOf(NetworkTag(1, "BMX", "shop"))
        ),
        networkVendor(
            id = 2,
            name = "Cafe & Restaurant",
            favorite = true,
            categories = listOf(NetworkCategory(11, media(), "Cafe")),
            tags = listOf(NetworkTag(2, "Coffee", "shop"))
        )
    )

    val domainVendors: List<Vendor> = listOf(
        Vendor(
            id = 1,
            companyName = "Florists + Fashion",
            coverPhotoUrl = "",
            areaServed = "Newry",
            isFavorite = false,
            categories = listOf(Category(10, "Florists", "")),
            tags = listOf("BMX")
        ),
        Vendor(
            id = 2,
            companyName = "Cafe & Restaurant",
            coverPhotoUrl = "",
            areaServed = "Ukraine",
            isFavorite = true,
            categories = listOf(Category(11, "Coffee", "")),
            tags = listOf("Espresso")
        )
    )
}

class FakeApiVendors(
    private val vendors: List<NetworkVendor> = TestData.networkVendors
) : ApiVendors {

    var callCount = 0
        private set

    override suspend fun getVendors(): List<NetworkVendor> {
        callCount++
        return vendors
    }
}

class FailingApiVendors : ApiVendors {
    override suspend fun getVendors(): List<NetworkVendor> =
        throw RuntimeException("network error")
}
