package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemResponseBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.ResultModel

class ResponseAdapter(
    private val onClickResponse: OnClickManage.OnClickResponse
) : RecyclerView.Adapter<ResponseAdapter.MyViewHolder>() {

    private var listData: List<ResultModel> = emptyList()

    fun setListResponse(listData: List<ResultModel>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemResponseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResponseBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resultModel = listData[position]



        holder.binding.apply {

            textViewItemResponseLessonIn.text = resultModel.lesson
            textViewItemResponseDateIn.text = resultModel.dateTime
            textViewItemResponseRateIn.text = resultModel.responseRate
            textViewItemResponseQuestionCountIn.text = "${resultModel.questionCount}"
            textViewItemResponseTrueAnswerIn.text = "${resultModel.trueAnswer}"
            textViewItemResponseFalseAnswerIn.text = "${resultModel.falseAnswer}"
            textViewItemResponseNotAnswerIn.text = "${resultModel.notAnswer}"

        }

        holder.itemView.setOnClickListener {
            onClickResponse.onClickResponse(resultModel)
        }

        holder.itemView.setOnLongClickListener {
            onClickResponse.onLongClickResponse(resultModel)
            true
        }

    }

    override fun getItemCount(): Int = listData.size
}