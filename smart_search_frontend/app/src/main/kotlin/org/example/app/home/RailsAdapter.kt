package org.example.app.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.VodItem

class RailsAdapter(
    private val rails: List<VodRail>,
    private val onVodSelected: (VodItem) -> Unit
) : RecyclerView.Adapter<RailsAdapter.RailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rail, parent, false)
        return RailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RailViewHolder, position: Int) {
        holder.bind(rails[position], onVodSelected)
    }

    override fun getItemCount(): Int = rails.size

    class RailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.railTitle)
        private val itemsRecyclerView: RecyclerView = itemView.findViewById(R.id.railItemsRecyclerView)

        fun bind(rail: VodRail, onVodSelected: (VodItem) -> Unit) {
            title.text = rail.title
            itemsRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            itemsRecyclerView.adapter = VodCardAdapter(rail.items, onVodSelected)
        }
    }
}
