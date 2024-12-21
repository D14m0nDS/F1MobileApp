package com.antoan.f1app.api.models

import com.antoan.f1app.api.models.enums.SessionType

data class Session(
    val sessionType: SessionType,
    val sessionDateTime: String,
    val sessionResults: Map<String, Pair<Int, Int>>,
)