package com.youarelaunched.challenge.middle.repository

import com.youarelaunched.challenge.testutil.FakeApiVendors
import com.youarelaunched.challenge.testutil.TestData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VendorRepositoryTest {

    @Test
    fun `getVendors maps network data to domain`() = runBlocking {
        val repository = VendorRepository(FakeApiVendors())

        val result = repository.getVendors()

        assertEquals(2, result.size)
        assertEquals("Florists + Fashion", result.first().companyName)
    }

    @Test
    fun `getVendors is loaded only once and then cached`() = runBlocking {
        val api = FakeApiVendors()
        val repository = VendorRepository(api)

        repository.getVendors()
        repository.getVendors()

        assertEquals(1, api.callCount)
    }

    @Test
    fun `filter with blank query returns everything`() {
        val repository = VendorRepository(FakeApiVendors())

        val result = repository.filter(TestData.domainVendors, "   ")

        assertEquals(TestData.domainVendors, result)
    }

    @Test
    fun `filter matches company name, category and tag`() {
        val repository = VendorRepository(FakeApiVendors())
        val vendors = TestData.domainVendors

        assertEquals(1, repository.filter(vendors, "florist").size) // by name + category
        assertEquals(1, repository.filter(vendors, "coffee").size)  // by category
        assertEquals(1, repository.filter(vendors, "bmx").size)     // by tag
        assertTrue(repository.filter(vendors, "no-match").isEmpty())
    }
}
