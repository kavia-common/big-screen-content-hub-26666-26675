package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import org.example.app.epg.EpgFragment
import org.example.app.favorites.FavoritesFragment
import org.example.app.home.HomeFragment
import org.example.app.search.SearchFragment

/**
 * Main entrypoint activity hosting the STB big-screen app.
 *
 * Layout:
 * - persistent top search bar
 * - left nav rail (Home/EPG/Favorites)
 * - content area hosts fragments
 */
class MainActivity : FragmentActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var navHome: Button
    private lateinit var navEpg: Button
    private lateinit var navFavorites: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        navHome = findViewById(R.id.navHome)
        navEpg = findViewById(R.id.navEpg)
        navFavorites = findViewById(R.id.navFavorites)

        navHome.setOnClickListener { showHome() }
        navEpg.setOnClickListener { showEpg() }
        navFavorites.setOnClickListener { showFavorites() }

        // Submit search with DPAD center / enter / IME action.
        searchEditText.setOnEditorActionListener { _, actionId, event ->
            val isEnter = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            val isSearch = actionId == EditorInfo.IME_ACTION_SEARCH
            if (isEnter || isSearch) {
                showSearch(searchEditText.text?.toString().orEmpty())
                true
            } else {
                false
            }
        }

        // Initial destination
        if (savedInstanceState == null) {
            showHome()
            // Prefer initial focus on search bar for TV remotes.
            searchEditText.requestFocus()
        }
    }

    private fun showHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, HomeFragment.newInstance(), HomeFragment.TAG)
            .commit()
    }

    private fun showEpg() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, EpgFragment.newInstance(), EpgFragment.TAG)
            .commit()
    }

    private fun showFavorites() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, FavoritesFragment.newInstance(), FavoritesFragment.TAG)
            .commit()
    }

    private fun showSearch(query: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainer, SearchFragment.newInstance(query), SearchFragment.TAG)
            .commit()
    }
}
