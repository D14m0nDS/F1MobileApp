package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.repositories.ConstructorsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ConstructorScreenViewModelTest : BaseViewModelTest() {
    private val fakeConstructor = Constructor("x","NameX","NatX", emptyList(), 0f)
    private val repo        = mockk<ConstructorsRepository>()
    private val apiSingleton= mockk<ApiSingleton>()

    @Test
    fun `loadConstructorInfo updates state and baseUrl`() = runTest {
        coEvery { repo.getConstructor("x") } returns fakeConstructor
        every { apiSingleton.getBaseUrl() } returns "https://base.url/"

        val vm = ConstructorScreenViewModel(repo, apiSingleton)
        vm.loadConstructorInfo("x")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeConstructor, vm.constructorInfo.value)
        assertEquals("https://base.url/", vm.baseUrl)
    }
}
