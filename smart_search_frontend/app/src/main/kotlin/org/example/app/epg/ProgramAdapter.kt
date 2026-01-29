package org.example.app.epg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.model.EpgProgram
import org.example.app.util.TimeFormatters

class ProgramAdapter(
    private val programs: List<EpgProgram>
) : RecyclerView.Adapter<ProgramAdapter.ProgramVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_epg_program, parent, false)
        return ProgramVH(view)
    }

    override fun onBindViewHolder(holder: ProgramVH, position: Int) {
        holder.bind(programs[position])
    }

    override fun getItemCount(): Int = programs.size

    class ProgramVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.programTitle)
        private val time: TextView = itemView.findViewById(R.id.programTime)
        private val desc: TextView = itemView.findViewById(R.id.programDesc)

        fun bind(program: EpgProgram) {
            title.text = program.title
            time.text = TimeFormatters.formatTimeRange(program.startEpochMs, program.endEpochMs)
            desc.text = program.description
        }
    }
}
