package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorStandings
import com.antoan.f1app.api.models.DriverStandings
import com.antoan.f1app.api.repositories.StandingsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class StandingsViewModelTest : BaseViewModelTest() {
    private val driverList = listOf(DriverStandings("d","Name","c","CName",100f,1,2,44))
    private val constructorList = listOf(ConstructorStandings("c","CName",200f,1,3))
    private val repo         = mockk<StandingsRepository>()
    private val apiSingleton = mockk<ApiSingleton>()

    @Test
    fun `init loads both standings`() = runTest {
        coEvery { repo.getDriverStandings() } returns driverList
        coEvery { repo.getConstructorStandings() } returns constructorList
        every { apiSingleton.getBaseUrl() } returns "base"

        val vm = StandingsViewModel(repo, apiSingleton)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(driverList, vm.driverStandings.value)
        assertEquals(constructorList, vm.constructorStandings.value)
        assertEquals("base", vm.baseUrl)
    }
}
