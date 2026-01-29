package org.example.app.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.VodItem

class VodCardAdapter(
    private val items: List<VodItem>,
    private val onSelected: (VodItem) -> Unit
) : RecyclerView.Adapter<VodCardAdapter.VodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vod_card, parent, false)
        return VodViewHolder(view)
    }

    override fun onBindViewHolder(holder: VodViewHolder, position: Int) {
        holder.bind(items[position], onSelected)
    }

    override fun getItemCount(): Int = items.size

    class VodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumb: ImageView = itemView.findViewById(R.id.vodThumb)
        private val title: TextView = itemView.findViewById(R.id.vodTitle)
        private val meta: TextView = itemView.findViewById(R.id.vodMeta)
        private val desc: TextView = itemView.findViewById(R.id.vodDesc)

        fun bind(item: VodItem, onSelected: (VodItem) -> Unit) {
            title.text = item.title
            meta.text = "${item.durationMinutes} min • ${item.category}"
            desc.text = item.description

            val resId = itemView.context.resources.getIdentifier(
                item.thumbnailResName,
                "drawable",
                itemView.context.packageName
            )
            if (resId != 0) {
                thumb.setBackgroundResource(resId)
            }

            itemView.setOnClickListener { onSelected(item) }
        }
    }
}
