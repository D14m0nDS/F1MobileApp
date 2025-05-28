package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorStandings
import com.antoan.f1app.api.models.DriverStandings
import com.antoan.f1app.api.services.F1Api
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.lang.RuntimeException

class StandingsRepositoryTest {

    private val fakeApi = mockk<F1Api>()
    private val initFlow = MutableStateFlow(true)
    private val apiSingleton = mockk<ApiSingleton> {
        every { isInitialized } returns initFlow
        every { getF1Api() }      returns fakeApi
    }

    private val repo = StandingsRepository(apiSingleton)

    @Test
    fun `getDriverStandings returns list when API succeeds`() = runTest {
        val list = listOf(DriverStandings("d","N","c","C",100f,1,2,44))
        coEvery { fakeApi.getDriverStandings("current") } returns list

        val result = repo.getDriverStandings()
        assertEquals(list, result)
    }

    @Test
    fun `getDriverStandings returns empty on exception`() = runTest {
        coEvery { fakeApi.getDriverStandings(any()) } throws RuntimeException()

        val result = repo.getDriverStandings()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getConstructorStandings returns list when API succeeds`() = runTest {
        val list = listOf(ConstructorStandings("c","C",200f,1,0))
        coEvery { fakeApi.getConstructorStandings("current") } returns list

        val result = repo.getConstructorStandings()
        assertEquals(list, result)
    }

    @Test
    fun `getConstructorStandings returns empty on exception`() = runTest {
        coEvery { fakeApi.getConstructorStandings(any()) } throws RuntimeException()

        val result = repo.getConstructorStandings()
        assertTrue(result.isEmpty())
    }
}
