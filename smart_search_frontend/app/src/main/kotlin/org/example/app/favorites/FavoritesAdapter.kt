package org.example.app.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.VodItem

class FavoritesAdapter(
    private val items: List<VodItem>,
    private val onRemove: (VodItem) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_row, parent, false)
        return FavVH(view)
    }

    override fun onBindViewHolder(holder: FavVH, position: Int) {
        holder.bind(items[position], onRemove)
    }

    override fun getItemCount(): Int = items.size

    class FavVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumb: ImageView = itemView.findViewById(R.id.favThumb)
        private val title: TextView = itemView.findViewById(R.id.favTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.favSubtitle)
        private val removeBtn: Button = itemView.findViewById(R.id.favRemove)

        fun bind(item: VodItem, onRemove: (VodItem) -> Unit) {
            title.text = item.title
            subtitle.text = "${item.durationMinutes} min • ${item.category}\n${item.description}"

            val resId = itemView.context.resources.getIdentifier(
                item.thumbnailResName,
                "drawable",
                itemView.context.packageName
            )
            if (resId != 0) {
                thumb.setBackgroundResource(resId)
            }

            removeBtn.setOnClickListener { onRemove(item) }
        }
    }
}
