package com.youarelaunched.challenge.middle.repository

import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.middle.mapper.toDomain
import com.youarelaunched.challenge.network.api.ApiVendors

class VendorRepository(
    private val apiVendors: ApiVendors
) {

    // Cache the parsed list so search doesn't re-read and re-parse the asset on every keystroke.
    private var cachedVendors: List<Vendor>? = null

    suspend fun getVendors(): List<Vendor> {
        return cachedVendors ?: apiVendors.getVendors()
            .map { it.toDomain() }
            .also { cachedVendors = it }
    }

    suspend fun searchVendors(query: String): List<Vendor> {
        val allVendors = getVendors()
        if (query.isBlank()) return allVendors

        val lowerQuery = query.lowercase()
        return allVendors.filter { vendor ->
            vendor.companyName.lowercase().contains(lowerQuery) ||
                    vendor.categories.any { it.name.lowercase().contains(lowerQuery) } ||
                    vendor.tags.any { it.lowercase().contains(lowerQuery) }
        }
    }
}
