package org.example.app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.DummyData
import org.example.app.data.FavoritesRepository
import org.example.app.model.EpgProgram
import org.example.app.model.VodItem

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var hintView: TextView

    private lateinit var vodRv: RecyclerView
    private lateinit var epgRv: RecyclerView
    private lateinit var favRv: RecyclerView

    private lateinit var repo: FavoritesRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = FavoritesRepository(requireContext())

        hintView = view.findViewById(R.id.searchHint)

        vodRv = view.findViewById(R.id.vodResultsRecyclerView)
        epgRv = view.findViewById(R.id.epgResultsRecyclerView)
        favRv = view.findViewById(R.id.favResultsRecyclerView)

        vodRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        epgRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        favRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val query = arguments?.getString(ARG_QUERY).orEmpty()
        render(query)
    }

    private fun render(query: String) {
        val q = query.trim()
        hintView.visibility = if (q.isEmpty()) View.VISIBLE else View.GONE

        val channels = DummyData.channels.associate { it.id to it.name }
        val epg = DummyData.generateEpg(hours = 4, slotMinutes = 30)
        val favorites = repo.getFavoriteIds()

        val results = SearchEngine.search(
            query = q,
            vod = DummyData.vodCatalog,
            epg = epg,
            channelNameById = channels,
            favoriteVodIds = favorites
        )

        vodRv.adapter = SearchVodAdapter(results.vod)
        epgRv.adapter = SearchEpgAdapter(results.epg, channels)
        favRv.adapter = SearchVodAdapter(results.favoritesVod)
    }

    companion object {
        const val TAG = "SearchFragment"
        private const val ARG_QUERY = "arg_query"

        fun newInstance(query: String): SearchFragment =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }
}

private class SearchVodAdapter(
    private val items: List<VodItem>
) : RecyclerView.Adapter<SearchVodAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindVod(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.resultTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.resultSubtitle)

        fun bindVod(item: VodItem) {
            title.text = item.title
            subtitle.text = "${item.durationMinutes} min • ${item.category}\n${item.description}"
        }
    }
}

private class SearchEpgAdapter(
    private val programs: List<EpgProgram>,
    private val channelNameById: Map<String, String>
) : RecyclerView.Adapter<SearchEpgAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(programs[position], channelNameById)
    }

    override fun getItemCount(): Int = programs.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.resultTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.resultSubtitle)

        fun bind(program: EpgProgram, channelNameById: Map<String, String>) {
            val channel = channelNameById[program.channelId] ?: "Unknown"
            title.text = "$channel • ${program.title}"
            subtitle.text = program.description
        }
    }
}
