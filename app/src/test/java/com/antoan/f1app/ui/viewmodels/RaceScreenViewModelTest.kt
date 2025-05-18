package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.repositories.RacesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class RaceScreenViewModelTest : BaseViewModelTest() {
    private val sample = Race()
    private val repo = mockk<RacesRepository>()

    @Test
    fun `loadRaceInfo updates race and loading`() = runTest {
        coEvery { repo.getRace("2025", 1) } returns sample

        val vm = RaceScreenViewModel(repo)

        assertTrue(vm.isLoading.value)

        vm.loadRaceInfo("2025", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(vm.isLoading.value)
        assertEquals(sample, vm.race.value)
    }
}
