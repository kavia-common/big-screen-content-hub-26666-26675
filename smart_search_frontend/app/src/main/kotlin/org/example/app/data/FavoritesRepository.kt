package org.example.app.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Repository persisting favorites using SharedPreferences.
 *
 * Stores a set of VOD item ids. Keeps API intentionally small for this demo.
 */
class FavoritesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getFavoriteIds(): Set<String> {
        return prefs.getStringSet(KEY_IDS, emptySet()) ?: emptySet()
    }

    fun isFavorite(id: String): Boolean {
        return getFavoriteIds().contains(id)
    }

    fun addFavorite(id: String) {
        val updated = getFavoriteIds().toMutableSet()
        updated.add(id)
        prefs.edit().putStringSet(KEY_IDS, updated).apply()
    }

    fun removeFavorite(id: String) {
        val updated = getFavoriteIds().toMutableSet()
        updated.remove(id)
        prefs.edit().putStringSet(KEY_IDS, updated).apply()
    }

    fun toggleFavorite(id: String): Boolean {
        val updated = getFavoriteIds().toMutableSet()
        val nowFavorite = if (updated.contains(id)) {
            updated.remove(id)
            false
        } else {
            updated.add(id)
            true
        }
        prefs.edit().putStringSet(KEY_IDS, updated).apply()
        return nowFavorite
    }

    private companion object {
        private const val PREFS_NAME = "ocean_favorites"
        private const val KEY_IDS = "favorite_vod_ids"
    }
}
