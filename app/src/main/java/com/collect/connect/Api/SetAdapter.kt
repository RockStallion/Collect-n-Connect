package com.collect.connect.Api

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collect_n_connect.R
import com.squareup.picasso.Picasso

class SetAdapter(private val sets: List<LegoSet>) : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtYear: TextView = itemView.findViewById(R.id.txtYear)
        val imgSet: ImageView = itemView.findViewById(R.id.imgSet)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val item = sets[position]
        holder.txtName.text = item.name
        holder.txtYear.text = "Year: ${item.year} | Pieces: ${item.num_parts}"
        Picasso.get().load(item.set_img_url).into(holder.imgSet)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.set_url))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = sets.size
}