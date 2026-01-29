package org.example.app.favorites

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.DummyData
import org.example.app.data.FavoritesRepository

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var repo: FavoritesRepository
    private lateinit var emptyView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repo = FavoritesRepository(requireContext())

        emptyView = view.findViewById(R.id.favEmpty)
        recyclerView = view.findViewById(R.id.favoritesRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        render()
    }

    private fun render() {
        val favoriteIds = repo.getFavoriteIds()
        val items = DummyData.vodCatalog.filter { favoriteIds.contains(it.id) }

        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        recyclerView.adapter = FavoritesAdapter(
            items = items,
            onRemove = { vod ->
                repo.removeFavorite(vod.id)
                render()
            }
        )
    }

    companion object {
        const val TAG = "FavoritesFragment"
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }
}
