package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.services.F1Api
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class DriversRepositoryTest {

    private val fakeApi = mockk<F1Api>()
    private val initFlow = MutableStateFlow(true)
    private val apiSingleton = mockk<ApiSingleton> {
        every { isInitialized } returns initFlow
        every { getF1Api() }      returns fakeApi
    }

    private val repo = DriversRepository(apiSingleton)

    @Test
    fun `getAllDrivers returns list when API succeeds`() = runTest {
        val list = listOf(
            Driver("d1","A",30,1,"N","c1","C1",100f, emptyList(), ""),
            Driver("d2","B",28,2,"N","c2","C2", 50f, emptyList(), "")
        )
        coEvery { fakeApi.getAllDrivers("current") } returns list

        val result = repo.getAllDrivers()
        assertEquals(list, result)
    }

    @Test
    fun `getAllDrivers returns empty on exception`() = runTest {
        coEvery { fakeApi.getAllDrivers(any()) } throws IOException("fail")

        val result = repo.getAllDrivers()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getDriver returns object when API succeeds`() = runTest {
        val driver = Driver("dx","Name",25,7,"N","c","C",42f, emptyList(), "")
        coEvery { fakeApi.getDriverInfo("dx") } returns driver

        val result = repo.getDriver("dx")
        assertEquals(driver, result)
    }

    @Test
    fun `getDriver returns default on exception`() = runTest {
        coEvery { fakeApi.getDriverInfo(any()) } throws RuntimeException()

        val result = repo.getDriver("any")
        assertEquals(Driver(), result)
    }
}
