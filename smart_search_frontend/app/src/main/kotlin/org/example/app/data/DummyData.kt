package org.example.app.data

import org.example.app.model.Channel
import org.example.app.model.EpgProgram
import org.example.app.model.VodItem
import java.util.concurrent.TimeUnit

object DummyData {
    val categories: List<String> = listOf(
        "Trending",
        "New Releases",
        "Action",
        "Drama",
        "Comedy",
        "Documentary"
    )

    val vodCatalog: List<VodItem> = buildList {
        val thumbs = listOf("thumb_blue", "thumb_amber", "thumb_slate")
        var idx = 1
        for (cat in categories) {
            for (i in 1..8) { // 6 * 8 = 48 items
                val id = "vod_${idx++}"
                add(
                    VodItem(
                        id = id,
                        title = "$cat Title $i",
                        description = "A modern $cat story with compelling moments and a clean TV-friendly presentation.",
                        durationMinutes = 80 + (i * 5),
                        category = cat,
                        thumbnailResName = thumbs[(i - 1) % thumbs.size]
                    )
                )
            }
        }
    }

    val channels: List<Channel> = (1..12).map { i ->
        Channel(
            id = "ch_$i",
            name = "Channel $i"
        )
    }

    /**
     * Generates programs for the next [hours] hours in fixed [slotMinutes] slots.
     */
    fun generateEpg(hours: Int = 4, slotMinutes: Int = 30): List<EpgProgram> {
        val now = System.currentTimeMillis()
        val windowStart = now - (now % TimeUnit.MINUTES.toMillis(slotMinutes.toLong()))
        val slotMs = TimeUnit.MINUTES.toMillis(slotMinutes.toLong())
        val slots = (hours * 60) / slotMinutes

        var programIdx = 1
        return buildList {
            for (ch in channels) {
                for (s in 0 until slots) {
                    val start = windowStart + (s * slotMs)
                    val end = start + slotMs
                    add(
                        EpgProgram(
                            id = "p_${programIdx++}",
                            channelId = ch.id,
                            title = "Show ${s + 1}",
                            description = "Episode ${(s % 6) + 1}: Highlights, interviews and features.",
                            startEpochMs = start,
                            endEpochMs = end
                        )
                    )
                }
            }
        }
    }
}
