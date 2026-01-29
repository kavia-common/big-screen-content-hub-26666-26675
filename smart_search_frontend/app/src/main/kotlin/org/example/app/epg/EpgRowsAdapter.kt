package org.example.app.epg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.Channel
import org.example.app.model.EpgProgram

class EpgRowsAdapter(
    private val channels: List<Channel>,
    private val programsByChannel: Map<String, List<EpgProgram>>
) : RecyclerView.Adapter<EpgRowsAdapter.RowVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_epg_row, parent, false)
        return RowVH(view)
    }

    override fun onBindViewHolder(holder: RowVH, position: Int) {
        val ch = channels[position]
        val programs = programsByChannel[ch.id].orEmpty()
        holder.bind(programs)
    }

    override fun getItemCount(): Int = channels.size

    class RowVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val programsRecyclerView: RecyclerView = itemView.findViewById(R.id.programsRecyclerView)

        fun bind(programs: List<EpgProgram>) {
            programsRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            programsRecyclerView.adapter = ProgramAdapter(programs)
        }
    }
}
