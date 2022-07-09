package com.example.hkspower.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hkspower.R
import com.example.hkspower.data.api.apis.model.ModelDoa
import kotlinx.android.synthetic.main.list_item_doa.view.*

 class AdapterDoa(private val modelBacaan: MutableList<ModelDoa>) :
    RecyclerView.Adapter<AdapterDoa.ListViewHolder>() {

    private val modelBacaanListFull: List<ModelDoa>


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_doa, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, i: Int) {
        val dataModel = modelBacaan[i]
       // listViewHolder.tvId.text = "Mid "+dataModel.mid
        listViewHolder.tvTitle.text = "Mid :"+dataModel.mid
        listViewHolder.tvTid.text = "Tid :"+dataModel.tid
        listViewHolder.tvArabic.text = "₹ "+dataModel.amt
        listViewHolder.tvLatin.text = dataModel.narrations

    }

    override fun getItemCount(): Int {
        return modelBacaan.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvTid: TextView
        var tvArabic: TextView
        var tvLatin: TextView
        var tvTerjemahan: TextView

        init {
            tvTitle = itemView.tvTitle
             tvTid = itemView.tvtid
            tvArabic = itemView.tvArabic
            tvLatin = itemView.tvLatin
            tvTerjemahan = itemView.tvTerjemahan
        }
    }

    init {
        modelBacaanListFull = ArrayList(modelBacaan)
    }

}