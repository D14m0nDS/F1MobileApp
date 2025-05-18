package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.models.Driver
import com.antoan.f1app.api.repositories.DriversRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class AllDriversViewModelTest : BaseViewModelTest() {
    private val fakeDrivers = listOf(
        Driver("d1","Driver1",30,1,"C1","cId","cName",100f, emptyList(), ""),
        Driver("d2","Driver2",28,2,"C2","cId2","cName2", 80f, emptyList(), "")
    )
    private val repo = mockk<DriversRepository>()

    @Test
    fun `init loads drivers from repo`() = runTest {
        coEvery { repo.getAllDrivers() } returns fakeDrivers

        val vm = AllDriversViewModel(repo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeDrivers, vm.drivers.value)
    }
}
