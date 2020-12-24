package com.dsige.police.group.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.police.group.R
import com.dsige.police.group.data.local.model.Empresa
import com.dsige.police.group.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_combo.view.*

class EmpresaAdapter(private val listener: OnItemClickListener.EmpresaListener) :
    RecyclerView.Adapter<EmpresaAdapter.ViewHolder>() {

    private var menu = emptyList<Empresa>()

    fun addItems(list: List<Empresa>) {
        menu = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_combo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menu[position], listener)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(m: Empresa, listener: OnItemClickListener.EmpresaListener) = with(itemView) {
            textViewTitulo.text = m.nombreEmpresa
            itemView.setOnClickListener { v -> listener.onItemClick(m, v, adapterPosition) }
        }
    }
}