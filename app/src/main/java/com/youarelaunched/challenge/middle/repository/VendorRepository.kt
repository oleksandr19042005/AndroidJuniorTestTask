package com.youarelaunched.challenge.middle.repository

import com.youarelaunched.challenge.middle.domain.Vendor
import com.youarelaunched.challenge.middle.mapper.toDomain
import com.youarelaunched.challenge.network.api.ApiVendors

class VendorRepository(
    private val apiVendors: ApiVendors
) {

    // Cache the parsed list so it is read and mapped only once.
    private var cachedVendors: List<Vendor>? = null

    suspend fun getVendors(): List<Vendor> {
        return cachedVendors ?: apiVendors.getVendors()
            .map { it.toDomain() }
            .also { cachedVendors = it }
    }

    /**
     * Pure, synchronous filter over an already-loaded list — kept here so the
     * search rule lives in one place and is trivial to unit-test.
     */
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
