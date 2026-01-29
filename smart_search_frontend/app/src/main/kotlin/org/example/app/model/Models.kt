package org.example.app.model

/**
 * Simple models for the dummy STB app.
 */
data class VodItem(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val category: String,
    val thumbnailResName: String
)

data class Channel(
    val id: String,
    val name: String
)

data class EpgProgram(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String,
    val startEpochMs: Long,
    val endEpochMs: Long
)
