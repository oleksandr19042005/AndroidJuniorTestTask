package com.youarelaunched.challenge.data.mapper

import com.youarelaunched.challenge.network.models.NetworkCategory
import com.youarelaunched.challenge.network.models.NetworkTag
import com.youarelaunched.challenge.testutil.TestData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VendorMapperTest {

    @Test
    fun `maps all fields to the domain model`() {
        val network = TestData.networkVendor(
            id = 5,
            name = "Shop",
            area = "Kyiv",
            favorite = true,
            categories = listOf(NetworkCategory(10, TestData.media("svg-url"), "Florists")),
            tags = listOf(NetworkTag(1, "BMX", "shop"))
        )

        val domain = network.toDomain()

        assertEquals(5, domain.id)
        assertEquals("Shop", domain.companyName)
        assertEquals("Kyiv", domain.areaServed)
        assertTrue(domain.isFavorite)
        assertEquals(1, domain.categories.size)
        assertEquals("Florists", domain.categories.first().name)
        assertEquals("svg-url", domain.categories.first().imageUrl)
        assertEquals(listOf("BMX"), domain.tags)
    }

    @Test
    fun `null categories and tags map to empty lists`() {
        val network = TestData.networkVendor(
            id = 1,
            name = "Shop",
            categories = null,
            tags = null
        )

        val domain = network.toDomain()

        assertTrue(domain.categories.isEmpty())
        assertTrue(domain.tags.isEmpty())
    }
}
