package com.antoan.f1app.ui.components

import com.antoan.f1app.api.models.Result
import java.util.Locale

private val lapStatusRegex = Regex("^\\+\\s*(\\d+)\\s*laps?$", RegexOption.IGNORE_CASE)
private val timeRegex = Regex("^(?:(\\d+)\\s+days?\\s+)?(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d+)$")

fun formatResultDisplayText(result: Result, showWinnerTotalTime: Boolean = false): String {
    return formatRaceOutcome(
        position = result.position,
        time = result.time,
        status = result.status,
        showWinnerTotalTime = showWinnerTotalTime
    )
}

fun formatRaceOutcome(
    position: Float,
    time: String?,
    status: String,
    showWinnerTotalTime: Boolean = false
): String {
    val normalizedStatus = status.trim().lowercase(Locale.US)

    if (normalizedStatus.contains("disqual") || normalizedStatus == "dsq") {
        return "DSQ"
    }

    parseLapCount(status)?.let { laps ->
        return formatLapCount(laps)
    }

    if (normalizedStatus.contains("lap")) {
        return formatLapCount(1)
    }

    if (normalizedStatus.isNotBlank() && normalizedStatus != "finished") {
        return "DNF"
    }

    val parsedTime = parseRaceTimeMs(time)
    if (position == 1f) {
        return if (showWinnerTotalTime && parsedTime != null) {
            formatRaceTime(parsedTime)
        } else {
            "Winner"
        }
    }

    if (parsedTime == null) {
        return formatLapCount(1)
    }

    return formatGap(parsedTime)
}

private fun parseLapCount(status: String): Int? {
    val match = lapStatusRegex.find(status.trim()) ?: return null
    return match.groupValues.getOrNull(1)?.toIntOrNull()
}

private fun parseRaceTimeMs(raw: String?): Long? {
    if (raw.isNullOrBlank()) return null

    val match = timeRegex.find(raw.trim()) ?: return null
    val days = match.groupValues.getOrNull(1)?.toLongOrNull() ?: 0L
    val hours = match.groupValues.getOrNull(2)?.toLongOrNull() ?: return null
    val minutes = match.groupValues.getOrNull(3)?.toLongOrNull() ?: return null
    val seconds = match.groupValues.getOrNull(4)?.toLongOrNull() ?: return null
    val millis = match.groupValues.getOrNull(5)?.take(3)?.padEnd(3, '0')?.toLongOrNull() ?: return null

    return days * 24L * 60L * 60L * 1000L +
        hours * 60L * 60L * 1000L +
        minutes * 60L * 1000L +
        seconds * 1000L +
        millis
}

private fun formatRaceTime(ms: Long): String {
    val hours = ms / 3_600_000
    val minutes = (ms % 3_600_000) / 60_000
    val seconds = (ms % 60_000) / 1_000
    val millis = ms % 1_000

    return if (hours > 0) {
        "%d:%02d:%02d.%03d".format(hours, minutes, seconds, millis)
    } else {
        "%02d:%02d.%03d".format(minutes, seconds, millis)
    }
}

private fun formatGap(ms: Long): String {
    val hours = ms / 3_600_000
    val minutes = (ms % 3_600_000) / 60_000
    val seconds = (ms % 60_000) / 1_000
    val millis = ms % 1_000

    return if (hours > 0) {
        "+%d:%02d:%02d.%03d".format(hours, minutes, seconds, millis)
    } else {
        "+%d:%02d.%03d".format(minutes, seconds, millis)
    }
}

private fun formatLapCount(laps: Int): String {
    return if (laps == 1) {
        "+1 lap"
    } else {
        "+%d laps".format(laps)
    }
}
