package org.example.app.epg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.Channel

class ChannelAdapter(
    private val channels: List<Channel>
) : RecyclerView.Adapter<ChannelAdapter.ChannelVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_epg_channel, parent, false)
        return ChannelVH(view)
    }

    override fun onBindViewHolder(holder: ChannelVH, position: Int) {
        holder.bind(channels[position])
    }

    override fun getItemCount(): Int = channels.size

    class ChannelVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.channelName)

        fun bind(channel: Channel) {
            name.text = channel.name
        }
    }
}
