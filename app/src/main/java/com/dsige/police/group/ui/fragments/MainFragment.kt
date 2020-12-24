package com.dsige.police.group.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsige.police.group.R
import com.dsige.police.group.data.local.model.Empresa
import com.dsige.police.group.data.local.model.Estado
import com.dsige.police.group.data.local.model.Filtro
import com.dsige.police.group.data.local.model.ParteDiario
import com.dsige.police.group.data.viewModel.OtViewModel
import com.dsige.police.group.data.viewModel.ViewModelFactory
import com.dsige.police.group.helper.Util
import com.dsige.police.group.ui.activities.FormActivity
import com.dsige.police.group.ui.adapters.EmpresaAdapter
import com.dsige.police.group.ui.adapters.EstadoAdapter
import com.dsige.police.group.ui.adapters.ParteDiarioAdapter
import com.dsige.police.group.ui.listeners.OnItemClickListener
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : DaggerFragment(), View.OnClickListener, TextView.OnEditorActionListener {

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (v.text.isNotEmpty()) {
            f.search = v.text.toString()
            val json = Gson().toJson(f)
            otViewModel.search.value = json
        }
        return false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editTextEmpresa -> spinnerDialog(1, "Empresas")
            R.id.editTextEstado -> spinnerDialog(2, "Estados")
            R.id.fab -> startActivity(
                Intent(context, FormActivity::class.java)
                    .putExtra("otId", otId)
                    .putExtra("usuarioId", usuarioId)
                    .putExtra("empresaId", empresaId)
            )
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var otViewModel: OtViewModel
    private var usuarioId: Int = 0
    private var empresaId: Int = 0
    private var otId: Int = 0
    lateinit var f: Filtro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuarioId = it.getInt(ARG_PARAM1)
            empresaId = it.getInt(ARG_PARAM2)
        }
        f = Filtro("", 4, 3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        otViewModel =
            ViewModelProvider(this, viewModelFactory).get(OtViewModel::class.java)

        editTextEstado.setText(String.format("Enviado al Efectivo Policial"))
        otViewModel.search.value = Gson().toJson(f)

        val otAdapter = ParteDiarioAdapter(object : OnItemClickListener.ParteDiarioListener {
            override fun onItemClick(p: ParteDiario, view: View, position: Int) {
                val popupMenu = PopupMenu(context!!, view)
                popupMenu.menu.add(0, 1, 0, getText(R.string.edit))
//                popupMenu.menu.add(0, 2, 0, getText(R.string.mapa))
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        1 -> startActivity(
                            Intent(context, FormActivity::class.java)
                                .putExtra("otId", p.parteDiarioId)
                                .putExtra("usuarioId", p.usuarioId)
                                .putExtra("empresaId", p.empresaId)
                        )
//                        2 -> startActivity(
//                            Intent(context, OtMapActivity::class.java)
//                                .putExtra("latitud", o.latitud)
//                                .putExtra("longitud", o.longitud)
//                                .putExtra("title", o.nombreEmpresa)
//                        )
                    }
                    false
                }
                popupMenu.show()
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = otAdapter
        otViewModel.getOts()
            .observe(viewLifecycleOwner, Observer(otAdapter::submitList))

        otViewModel.getMaxIdOt().observe(viewLifecycleOwner, { s ->
            otId = if (s != null) {
                s + 1
            } else
                1
        })

        editTextEstado.setOnClickListener(this)
        editTextEmpresa.setOnClickListener(this)
        editTextSearch.setOnEditorActionListener(this)
        fab.setOnClickListener(this)

        otViewModel.mensajeError.observe(viewLifecycleOwner, {
            Util.toastMensaje(context!!, it, false)
        })
    }

    private fun spinnerDialog(tipo: Int, title: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(context).inflate(R.layout.dialog_combo, null)
        val progressBar: ProgressBar = v.findViewById(R.id.progressBar)
        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        progressBar.visibility = View.GONE
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, DividerItemDecoration.VERTICAL
            )
        )
        textViewTitulo.text = title
        when (tipo) {
            1 -> {
                val empresaAdapter = EmpresaAdapter(object : OnItemClickListener.EmpresaListener {
                    override fun onItemClick(e: Empresa, view: View, position: Int) {
                        f.empresaId = e.empresaId
                        otViewModel.search.value = Gson().toJson(f)
                        editTextEmpresa.setText(e.nombreEmpresa)
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = empresaAdapter
                otViewModel.getEmpresas().observe(this, {
                    empresaAdapter.addItems(it)
                })
            }
            2 -> {
                val estadoAdapter = EstadoAdapter(object : OnItemClickListener.EstadoListener {
                    override fun onItemClick(e: Estado, view: View, position: Int) {
                        f.estadoId = e.estadoId
                        otViewModel.search.value = Gson().toJson(f)
                        editTextEstado.setText(e.abreviatura)
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = estadoAdapter
                otViewModel.getEstados().observe(this, {
                    estadoAdapter.addItems(it)
                })
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(p1: Int, p2: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, p1)
                    putInt(ARG_PARAM2, p2)
                }
            }
    }
}