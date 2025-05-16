package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.repositories.ConstructorsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AllConstructorsViewModelTest : BaseViewModelTest() {
    private val fakeConstructors = listOf(
        Constructor("id1","Name1","Country1", emptyList(), 10f),
        Constructor("id2","Name2","Country2", emptyList(), 20f),
    )
    private val repo = mockk<ConstructorsRepository>()

    @Test
    fun `init loads constructors from repo`() = runTest {
        coEvery { repo.getAllConstructors() } returns fakeConstructors

        val vm = AllConstructorsViewModel(repo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeConstructors, vm.constructors.value)
    }
}
