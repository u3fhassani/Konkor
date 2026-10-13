package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemYearsBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.YearModel

class YearAdapter(
    private val onClickYear: OnClickManage.OnClickYear,
    private val listData: List<YearModel>
) : RecyclerView.Adapter<YearAdapter.MyViewHolder>() {

//    private var listData: List<YearModel> = emptyList()
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setAdapterList(listData: List<YearModel>) {
//        this.listData = listData
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemYearsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val yearModel = listData[position]

        holder.binding.textViewItemYearIn.text = "${yearModel.year}"

        if (yearModel.isNewSystem) {
            holder.binding.textViewItemYearNewSystem.text = "نظام جدید"
        } else {
            holder.binding.textViewItemYearNewSystem.text = "نظام قدیم"
        }
        if (yearModel.isAbroad) {
            holder.binding.textViewItemYearAbroad.text = "خارح از کشور"
        } else {
            holder.binding.textViewItemYearAbroad.text = "داخل کشور"
        }

        holder.binding.relativeLayoutItemYear.setOnClickListener {
            onClickYear.onClickYear(listData[position], position)
        }

    }

    override fun getItemCount(): Int = listData.size

    inner class MyViewHolder(var binding: ItemYearsBinding) :
        RecyclerView.ViewHolder(binding.root)
}