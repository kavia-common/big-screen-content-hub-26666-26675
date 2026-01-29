package org.example.app.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.DummyData
import org.example.app.player.PlayerActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var railsRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        railsRecyclerView = view.findViewById(R.id.railsRecyclerView)
        railsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val byCategory = DummyData.vodCatalog.groupBy { it.category }
        val rails = DummyData.categories.mapNotNull { cat ->
            val items = byCategory[cat].orEmpty()
            if (items.isEmpty()) null else VodRail(cat, items)
        }

        railsRecyclerView.adapter = RailsAdapter(
            rails = rails,
            onVodSelected = { vod ->
                startActivity(Intent(requireContext(), PlayerActivity::class.java).apply {
                    putExtra(PlayerActivity.EXTRA_TITLE, vod.title)
                })
            }
        )
    }

    companion object {
        const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment = HomeFragment()
    }
}
