package org.example.app.search

import org.example.app.model.EpgProgram
import org.example.app.model.VodItem
import java.util.Locale

sealed class SearchResult {
    data class Vod(val item: VodItem) : SearchResult()
    data class Epg(val program: EpgProgram, val channelName: String) : SearchResult()
    data class FavoriteVod(val item: VodItem) : SearchResult()
}

/**
 * Simple contains-based case-insensitive search.
 */
object SearchEngine {

    /**
     * PUBLIC_INTERFACE
     * Performs global search across VOD, EPG, and Favorites.
     */
    fun search(
        query: String,
        vod: List<VodItem>,
        epg: List<EpgProgram>,
        channelNameById: Map<String, String>,
        favoriteVodIds: Set<String>
    ): SearchGroupedResults {
        val q = query.trim()
        if (q.isEmpty()) {
            return SearchGroupedResults(emptyList(), emptyList(), emptyList())
        }
        val qLower = q.lowercase(Locale.getDefault())

        val vodMatches = vod.filter { matches(qLower, it.title, it.description) }
        val epgMatches = epg.filter { matches(qLower, it.title, it.description) }
        val favoriteMatches = vod.filter { favoriteVodIds.contains(it.id) }
            .filter { matches(qLower, it.title, it.description) }

        return SearchGroupedResults(vodMatches, epgMatches, favoriteMatches)
    }

    private fun matches(qLower: String, title: String, desc: String): Boolean {
        return title.lowercase(Locale.getDefault()).contains(qLower) ||
            desc.lowercase(Locale.getDefault()).contains(qLower)
    }
}

data class SearchGroupedResults(
    val vod: List<VodItem>,
    val epg: List<EpgProgram>,
    val favoritesVod: List<VodItem>
)
