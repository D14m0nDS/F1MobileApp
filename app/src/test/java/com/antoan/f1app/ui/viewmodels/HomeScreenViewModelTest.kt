package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.repositories.RacesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class HomeScreenViewModelTest : BaseViewModelTest() {
    private val sampleSchedule = Schedule(2025, listOf(Race()))
    private val repo         = mockk<RacesRepository>()
    private val apiSingleton = mockk<ApiSingleton>()

    @Test
    fun `init loads schedule then flips loading`() = runTest {
        coEvery { repo.getSchedule() } returns sampleSchedule
        every { apiSingleton.getBaseUrl() } returns "https://base/"

        val vm = HomeScreenViewModel(repo, apiSingleton)

        assertTrue(vm.isLoading.value)

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(vm.isLoading.value)
        assertEquals(sampleSchedule, vm.schedule.value)
        assertEquals("https://base/", vm.baseUrl)
    }
}
