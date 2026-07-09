package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.ConstructorInfo
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.repositories.ConstructorsRepository
import com.antoan.f1app.api.repositories.RacesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ConstructorScreenViewModelTest : BaseViewModelTest() {
    private val fakeConstructor = ConstructorInfo("x","NameX","NatX", emptyList(), 0f, emptyList())
    private val repo = mockk<ConstructorsRepository>()
    private val racesRepository = mockk<RacesRepository>()
    private val apiSingleton = mockk<ApiSingleton>()

    @Test
    fun `loadConstructorInfo updates state and baseUrl`() = runTest {
        coEvery { repo.getConstructor("x") } returns fakeConstructor
        coEvery { racesRepository.getSchedule() } returns Schedule(2024)
        every { apiSingleton.getBaseUrl() } returns "https://base.url/"

        val vm = ConstructorScreenViewModel(repo, racesRepository, apiSingleton)
        vm.loadConstructorInfo("x")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeConstructor, vm.constructorInfo.value)
        assertEquals("https://base.url/", vm.baseUrl)
    }
}
