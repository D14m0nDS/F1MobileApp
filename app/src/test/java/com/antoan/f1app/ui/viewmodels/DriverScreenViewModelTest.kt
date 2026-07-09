package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.repositories.RacesRepository
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.repositories.DriversRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DriverScreenViewModelTest : BaseViewModelTest() {
    private val fakeDriver = Driver("d","Name",25,5,"Nat","cId","cName",50f, emptyList(), "")
    private val repo = mockk<DriversRepository>()
    private val racesRepository = mockk<RacesRepository>()
    private val apiSingleton = mockk<ApiSingleton>()

    @Test
    fun `loadDriverInfo updates state`() = runTest {
        coEvery { repo.getDriver("d") } returns fakeDriver
        coEvery { racesRepository.getSchedule() } returns Schedule(2024)
        every { apiSingleton.getBaseUrl() } returns "base"

        val vm = DriverScreenViewModel(repo, racesRepository, apiSingleton)
        vm.loadDriverInfo("d")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeDriver, vm.driverInfo.value)
    }
}
