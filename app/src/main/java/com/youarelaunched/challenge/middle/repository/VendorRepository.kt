package com.youarelaunched.challenge.middle.repository

import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.middle.mapper.toDomain
import com.youarelaunched.challenge.network.api.ApiVendors

class VendorRepository(
    private val apiVendors: ApiVendors
) {
    private var cachedVendors: List<Vendor>? = null

    suspend fun getVendors(): List<Vendor> {
        return cachedVendors ?: apiVendors.getVendors()
            .map { it.toDomain() }
            .also { cachedVendors = it }
    }
    fun filter(vendors: List<Vendor>, query: String): List<Vendor> {
        if (query.isBlank()) return vendors

        val normalizedQuery = query.trim().lowercase()
        return vendors.filter { vendor ->
            vendor.companyName.lowercase().contains(normalizedQuery) ||
                vendor.categories.any { it.name.lowercase().contains(normalizedQuery) } ||
                vendor.tags.any { it.lowercase().contains(normalizedQuery) }
        }
    }
}
