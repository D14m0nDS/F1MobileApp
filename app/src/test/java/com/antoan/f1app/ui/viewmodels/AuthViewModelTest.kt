package com.antoan.f1app.ui.viewmodels

import com.antoan.f1app.api.models.AuthResponse
import com.antoan.f1app.api.models.LoginRequest
import com.antoan.f1app.api.services.AuthApi
import com.antoan.f1app.api.ApiSingleton
import com.antoan.f1app.network.TokenManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.runCurrent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest : BaseViewModelTest() {

    private val authApi      = mockk<AuthApi>()
    private val apiSingleton = mockk<ApiSingleton>()
    private val tokenManager = mockk<TokenManager>(relaxed = true)

    @Before
    fun setup() {
        every { apiSingleton.getAuthApi() } returns authApi
        every { tokenManager.accessToken } returns null
    }

    @Test
    fun loginSuccessSetsIsLoggedInAndTokens() = runTest {
        coEvery { authApi.login(any<LoginRequest>()) } returns
                Response.success(AuthResponse("A-TOK", "R-TOK"))

        val vm = AuthViewModel(apiSingleton, tokenManager).apply {
            email = "foo@bar.com"
            password = "secret"
        }

        vm.login()
        runCurrent()

        assertTrue(vm.isLoggedIn.value)
        verify {
            tokenManager.accessToken  = "A-TOK"
            tokenManager.refreshToken = "R-TOK"
        }
    }

    @Test
    fun loginFailureSetsErrorAndLeavesIsLoggedInFalse() = runTest {
        coEvery { authApi.login(any<LoginRequest>()) } returns
                Response.error(
                    401,
                    """{"message":"Bad creds"}"""
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )

        val vm = AuthViewModel(apiSingleton, tokenManager).apply {
            email = "foo@bar.com"
            password = "wrongpass"
        }

        vm.login()
        runCurrent()

        assertNotNull(vm.error)
        assertTrue(vm.error!!.contains("Error 401"))
        assertFalse(vm.isLoggedIn.value)
    }
}
