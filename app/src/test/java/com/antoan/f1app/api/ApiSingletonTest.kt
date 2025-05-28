package com.antoan.f1app.api

import com.antoan.f1app.api.interceptors.AuthInterceptor
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class ApiSingletonTest {
    private lateinit var interceptor: dagger.Lazy<AuthInterceptor>
    private lateinit var api: ApiSingleton

    @Before fun setup() {
        interceptor = mockk()
        api = ApiSingleton(interceptor)
    }

    @Test fun `before initialize getF1Api throws`() {
        try {
            api.getF1Api()
            fail("Expected IllegalStateException")
        } catch (e: IllegalStateException) {
            assertTrue(e.message!!.contains("not initialized"))
        }
    }

    @Test fun `initialize sets baseUrl and isInitialized`() = runBlocking {
        every { interceptor.get() } returns mockk<AuthInterceptor>()
        api.initialize("https://example.com/")
        assertTrue(api.isInitialized.first())
        assertEquals("https://example.com/", api.getBaseUrl())
        val f1 = api.getF1Api()
        val auth = api.getAuthApi()
        assertNotNull(f1); assertNotNull(auth)
    }
}
