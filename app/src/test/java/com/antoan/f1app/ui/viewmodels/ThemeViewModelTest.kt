package com.antoan.f1app.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ThemeViewModelTest : BaseViewModelTest() {
    private val context = mockk<Context>()
    private val prefs   = mockk<SharedPreferences>(relaxed = true)
    private val editor  = mockk<SharedPreferences.Editor>(relaxed = true)

    @Before fun setup() {
        every { context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE) } returns prefs
        every { prefs.getBoolean("isDarkTheme", true) } returns true
        every { prefs.edit() } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
    }

    @Test fun `toggleTheme flips and saves preference`() = runTest {
        val vm = ThemeViewModel(context)
        assertTrue(vm.isDarkTheme.value)

        vm.toggleTheme()

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(vm.isDarkTheme.value)
        verify { editor.putBoolean("isDarkTheme", false) }
        verify { editor.apply() }
    }
}
