package org.example.app.epg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.DummyData

class EpgFragment : Fragment(R.layout.fragment_epg) {

    private lateinit var channelsRecyclerView: RecyclerView
    private lateinit var rowsRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        channelsRecyclerView = view.findViewById(R.id.channelsRecyclerView)
        rowsRecyclerView = view.findViewById(R.id.rowsRecyclerView)

        val channels = DummyData.channels
        val programs = DummyData.generateEpg(hours = 4, slotMinutes = 30)
        val programsByChannel = programs.groupBy { it.channelId }

        channelsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rowsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        channelsRecyclerView.adapter = ChannelAdapter(channels)

        rowsRecyclerView.adapter = EpgRowsAdapter(
            channels = channels,
            programsByChannel = programsByChannel
        )
    }

    companion object {
        const val TAG = "EpgFragment"
        fun newInstance(): EpgFragment = EpgFragment()
    }
}
