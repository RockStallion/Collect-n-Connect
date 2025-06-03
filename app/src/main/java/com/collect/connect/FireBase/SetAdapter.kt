package com.collect.connect.FireBase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collect_n_connect.R
import java.util.*

class SetsAdapter(private var setsList: List<SetModel>) :
    RecyclerView.Adapter<SetsAdapter.SetViewHolder>() {

    class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreSet)
        val tvDetalle: TextView = itemView.findViewById(R.id.tvDetalleSet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sets, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val set = setsList[position]
        holder.tvNombre.text = set.nombre
        // Por ejemplo, mostrar “piezas - año - tema”
        holder.tvDetalle.text = "Piezas: ${set.piezas} | Año: ${set.anio} | Tema: ${set.tema}"

        // Si quieres, formatea la fecha legible:
        // val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // holder.tvDetalle.append(" | Fecha: ${sdf.format(Date(set.timestamp))}")
    }

    override fun getItemCount(): Int = setsList.size

    fun actualizarDatos(nuevaLista: List<SetModel>) {
        setsList = nuevaLista
        notifyDataSetChanged()
    }
}