package com.dsige.police.group.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dsige.police.group.R
import com.dsige.police.group.data.local.model.ParteDiario
import com.dsige.police.group.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_parte_diario.view.*

class ParteDiarioAdapter(private val listener: OnItemClickListener.ParteDiarioListener) :
    PagedListAdapter<ParteDiario, ParteDiarioAdapter.ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = getItem(position)
        if (s != null) {
            holder.bind(s, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_parte_diario, parent, false)
        return ViewHolder(v!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(o: ParteDiario, listener: OnItemClickListener.ParteDiarioListener) =
            with(itemView) {
                textView1.text = String.format("N° %s", o.parteDiarioId)
                textView2.text = o.nombreServicio
                textView3.text = o.nroObraTD
                textView4.text = o.lugarTrabajoPD
                textView5.text = String.format("J. Coordinador : %s", o.nombreCoordinador)
                textView6.text = String.format("J. Cuadrilla : %s", o.nombreJefeCuadrilla)
                textView7.text = o.nombreEstado
                itemView.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
            }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ParteDiario>() {
            override fun areItemsTheSame(oldItem: ParteDiario, newItem: ParteDiario): Boolean =
                oldItem.parteDiarioId == newItem.parteDiarioId

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ParteDiario, newItem: ParteDiario): Boolean =
                oldItem == newItem
        }
    }
}