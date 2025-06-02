package com.collect.connect.Api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collect_n_connect.R

class SetsAdapter(private var sets: List<LegoSet>) : RecyclerView.Adapter<SetsAdapter.SetViewHolder>() {

    class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSetName: TextView = itemView.findViewById(R.id.textSetName)
        val tvSetYear: TextView = itemView.findViewById(R.id.textSetYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val set = sets[position]
        holder.tvSetName.text = set.name
        holder.tvSetYear.text = "Año: ${set.year}"
    }

    override fun getItemCount(): Int = sets.size

    fun updateData(newSets: List<LegoSet>) {
        sets = newSets
        notifyDataSetChanged()
    }
}