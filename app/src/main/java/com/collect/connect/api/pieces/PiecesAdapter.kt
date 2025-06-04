package com.collect.connect.api.pieces


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.collect.connect.api.PartItem
import com.example.collect_n_connect.R
import com.squareup.picasso.Picasso


class PiecesAdapter(
    private var parts: List<PartItem>
) : RecyclerView.Adapter<PiecesAdapter.PartViewHolder>() {

    inner class PartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPart)
        val nameText: TextView = itemView.findViewById(R.id.textViewPartName)
        val quantityText: TextView = itemView.findViewById(R.id.textViewPartQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_piece, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        val partItem = parts[position]
        holder.nameText.text = partItem.elementName
        holder.quantityText.text = "Cantidad: ${partItem.quantityInSet}"
        partItem.elementImageURL?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_sets)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = parts.size

    fun updateData(newParts: List<PartItem>) {
        parts = newParts
        notifyDataSetChanged()
    }
}