package com.antoan.f1app.api.repositories

import ScheduleResponse
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Race
import com.antoan.f1app.api.models.Schedule
import com.antoan.f1app.api.services.F1Api
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class RacesRepositoryTest {

    private val fakeApi = mockk<F1Api>()
    private val initFlow = MutableStateFlow(true)
    private val apiSingleton = mockk<ApiSingleton> {
        every { isInitialized } returns initFlow
        every { getF1Api() }      returns fakeApi
    }

    private val repo = RacesRepository(apiSingleton)

    @Test
    fun `getSchedule returns schedule when API succeeds`() = runTest {
        val sched = Schedule(2025, listOf(Race()))
        coEvery { fakeApi.getSchedule() } returns ScheduleResponse(sched)

        val result = repo.getSchedule()
        assertEquals(sched, result)
    }

    @Test
    fun `getSchedule returns default on exception`() = runTest {
        coEvery { fakeApi.getSchedule() } throws Exception("err")

        val result = repo.getSchedule()
        assertEquals(Schedule(0), result)
    }

    @Test
    fun `getRace returns race when API succeeds`() = runTest {
        val race = Race(id="r1", season=1, round=2, name="X", circuit=mockk(), date="", results=emptyList(), imageUrl="")
        coEvery { fakeApi.getRace("2025", 3) } returns race

        val result = repo.getRace("2025", 3)
        assertEquals(race, result)
    }

    @Test
    fun `getRace returns default on exception`() = runTest {
        coEvery { fakeApi.getRace(any(), any()) } throws Exception()

        val result = repo.getRace("s", 1)
        assertEquals(Race(), result)
    }
}
