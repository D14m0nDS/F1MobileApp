package com.antoan.f1app.network

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.core.content.edit

class TokenManager(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_tokens",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var accessToken: String?
        get() = sharedPreferences.getString("access_token", null)
        set(value) {
            if (!value.isNullOrEmpty()) {
                sharedPreferences.edit() { putString("access_token", value) }
            }
        }

    var refreshToken: String?
        get() = sharedPreferences.getString("refresh_token", null)
        set(value) {
            if (!value.isNullOrEmpty()) {
                sharedPreferences.edit() { putString("refresh_token", value) }
            }
        }

    fun clearTokens() {
        sharedPreferences.edit().apply {
            remove("access_token")
            remove("refresh_token")
            apply()
        }
    }
}