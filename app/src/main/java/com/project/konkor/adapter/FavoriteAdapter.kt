package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemFavoriteBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.LessonModel
import com.project.konkor.util.Constants


class FavoriteAdapter(
    private val onClickFavorite: OnClickManage.OnClickFavorite
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var dataList = listOf<LessonModel>()

    fun setFavoriteList(dataList: List<LessonModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        val lessonModel = dataList[position]
        holder.bind(lessonModel)

    }

    override fun getItemCount(): Int = dataList.count()

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lessonModel: LessonModel) {

            val text =
                "${lessonModel.lessonPersianName} ${lessonModel.category?.persianName()} سال ${lessonModel.idYear}"

            binding.textViewItemFavorite.text = text

            itemView.setOnClickListener {
                onClickFavorite.onClickItemFavorite(lessonModel)
            }

            if (lessonModel.isNewSystem!!) {
                binding.textViewItemFavoriteNewSystem.text = "<<نظام جدید>>"
            } else {
                binding.textViewItemFavoriteNewSystem.text = "<<نظام قدیم>>"
            }
        }

    }

    fun String.persianName(): String {
        return when (this) {
            Constants.ENSANI -> "انسانی"
            Constants.RIAZI -> "ریاضی"
            Constants.TAJROBI -> "تجربی"
            Constants.HONAR -> "هنر"
            Constants.ZABAN -> "زبان"
            else -> ""
        }
    }

}