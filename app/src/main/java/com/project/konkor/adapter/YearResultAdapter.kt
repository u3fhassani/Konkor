package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemResultBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.YearModel

class YearResultAdapter(
    private val onClickYearResult: OnClickManage.OnClickYearResult
) :
    RecyclerView.Adapter<YearResultAdapter.MyViewHolder>() {

    private var dataList: List<YearModel> = emptyList()


    fun setYearList(dataList: List<YearModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val yearModel = dataList[position]


        holder.binding.apply {
            textViewItemResultYear.text = "${yearModel.year}"

            if (yearModel.isNewSystem) {
                textViewItemResultSystem.text = "نظام جدید"
            } else {
                textViewItemResultSystem.text = "نظام قدیم"
            }

        }

        holder.itemView.setOnClickListener {
            onClickYearResult.onClickYearResult(yearModel)
        }


    }

    override fun getItemCount(): Int = dataList.size

    inner class MyViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)

}