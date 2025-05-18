package com.antoan.f1app.api.interceptors

import com.antoan.f1app.api.models.AuthResponse
import com.antoan.f1app.api.models.RefreshTokenRequest
import com.antoan.f1app.api.services.AuthApi
import com.antoan.f1app.network.TokenManager
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response as RetrofitResponse

class AuthInterceptorTest {
    private lateinit var tokenManager: TokenManager
    private lateinit var authApi: AuthApi
    private lateinit var chain: Interceptor.Chain
    private lateinit var interceptor: AuthInterceptor

    @Before fun setup() {
        tokenManager = mockk(relaxed = true)
        authApi      = mockk()
        chain        = mockk()
        interceptor  = AuthInterceptor(tokenManager, authApi)
    }

    private fun fakeResponse(code: Int) = Response.Builder()
        .request(Request.Builder().url("https://x/").build())
        .protocol(HTTP_1_1)
        .code(code)
        .message("")
        .body("".toResponseBody(null))
        .build()

    @Test fun `on 401 refreshes and retries with new token`() {
        every { tokenManager.accessToken } returns "OLD"
        every { tokenManager.refreshToken } returns "REFRESH"

        coEvery { authApi.refreshToken(RefreshTokenRequest("REFRESH")) } returns
                RetrofitResponse.success(AuthResponse("NEW","NEW_REF"))

        val captured = mutableListOf<Request>()
        every { chain.request() } returns Request.Builder().url("https://api/foo").build()
        every { chain.proceed(capture(captured)) } returnsMany listOf(
            fakeResponse(401),
            fakeResponse(200)
        )

        val resp = interceptor.intercept(chain)

        assert(resp.code == 200)

        assert(captured.size == 2)

        assert(captured[0].header("Authorization") == "Bearer OLD")

        assert(captured[1].header("Authorization") == "Bearer NEW")

        verify { tokenManager.accessToken = "NEW" }
    }

    @Test fun `logout invokes AuthApi and clears tokens`() = runBlocking {
        every { tokenManager.refreshToken } returns "RTOK"
        coEvery { authApi.logout("Bearer RTOK") } returns RetrofitResponse.success(Unit)

        interceptor.logout()

        coVerify { authApi.logout("Bearer RTOK") }
        verify  { tokenManager.clearTokens() }
    }
}
