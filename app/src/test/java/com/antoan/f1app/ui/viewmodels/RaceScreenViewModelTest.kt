package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.repositories.RacesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class RaceScreenViewModelTest : BaseViewModelTest() {
    private val sample = Race()
    private val repo = mockk<RacesRepository>()
    private val apiSingleton = mockk<ApiSingleton>()

    @Test
    fun `loadRaceInfo updates race and loading`() = runTest {
        coEvery { repo.getRace("2025", 1) } returns sample
        every { apiSingleton.getBaseUrl() } returns "base"

        val vm = RaceScreenViewModel(repo, apiSingleton)

        assertTrue(vm.isLoading.value)

        vm.loadRaceInfo("2025", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(vm.isLoading.value)
        assertEquals(sample, vm.race.value)
        assertEquals("base", vm.baseUrl)
    }
}
