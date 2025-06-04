package com.collect.connect.api.sets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.api.SetItem
import com.example.collect_n_connect.R
import com.squareup.picasso.Picasso
class SetsAdapter(
    private var sets: List<SetItem>,
    private val onItemClick: (SetItem) -> Unit
) : RecyclerView.Adapter<SetsAdapter.SetViewHolder>() {

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewSet)
        val nameText: TextView = itemView.findViewById(R.id.textViewSetName)
        val numberText: TextView = itemView.findViewById(R.id.textViewSetNumber)
        val yearText: TextView = itemView.findViewById(R.id.textViewSetYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_set3, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val setItem = sets[position]
        holder.nameText.text = setItem.name
        holder.numberText.text = "Número: ${setItem.number}"
        holder.yearText.text = "Año: ${setItem.year}"
        // Cargar imagen con Picasso o Glide
        setItem.thumbnailURL?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_pieces) // coloca un drawable de ic
                .into(holder.imageView)
        }
        holder.itemView.setOnClickListener {
            onItemClick(setItem)
        }
    }

    override fun getItemCount(): Int = sets.size

    fun updateData(newSets: List<SetItem>) {
        sets = newSets
        notifyDataSetChanged()
    }
}