package com.antoan.f1app.api.repositories

import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.api.models.Constructor
import com.antoan.f1app.api.services.F1Api
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class ConstructorsRepositoryTest {

    private val fakeApi = mockk<F1Api>()
    private val initFlow = MutableStateFlow(true)
    private val apiSingleton = mockk<ApiSingleton> {
        every { isInitialized } returns initFlow
        every { getF1Api() }      returns fakeApi
    }

    private val repo = ConstructorsRepository(apiSingleton)

    @Test
    fun `getAllConstructors returns list when API succeeds`() = runTest {
        val list = listOf(Constructor("id1","Name1","Nat1", emptyList(), 5f))
        coEvery { fakeApi.getAllConstructors("current") } returns list

        val result = repo.getAllConstructors()
        assertEquals(list, result)
    }

    @Test
    fun `getAllConstructors returns empty list on exception`() = runTest {
        coEvery { fakeApi.getAllConstructors(any()) } throws IOException("boom")

        val result = repo.getAllConstructors()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getConstructor returns object when API succeeds`() = runTest {
        val cons = Constructor("idX","NameX","NatX", listOf("d1","d2"), 12.5f)
        coEvery { fakeApi.getConstructorInfo("idX") } returns cons

        val result = repo.getConstructor("idX")
        assertEquals(cons, result)
    }

    @Test
    fun `getConstructor returns default on exception`() = runTest {
        coEvery { fakeApi.getConstructorInfo(any()) } throws RuntimeException("error")

        val result = repo.getConstructor("foo")
        assertEquals(Constructor(), result)
    }
}
