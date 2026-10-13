package com.project.konkor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemLessonButtonBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.LessonModel

class LessonAdapter(
    private val context: Context,
    private val onClickLesson: OnClickManage.OnClickLesson
) :
    RecyclerView.Adapter<LessonAdapter.LessenViewHolder>() {

    private var listData: List<LessonModel> = emptyList()


    fun setAdapterList(listData: List<LessonModel>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessenViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemLessonButtonBinding.inflate(inflater, parent, false)
        return LessenViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: LessenViewHolder, position: Int) {


        val lessonModel = listData[position]
        holder.binding.textViewItemLessen.text = lessonModel.lessonPersianName

        val density = context.resources.displayMetrics.density.toInt()
        val button = holder.binding.textViewItemLessen

        if (lessonModel.lesson.isEmpty()) {
            button.isEnabled = false
            holder.binding.textViewItemLessen.textSize = 18f
            holder.binding.textViewItemLessen
            val layoutParam = button.layoutParams as ViewGroup.MarginLayoutParams
            layoutParam.topMargin = 24 * density
        } else {
//            button.setStrokeColorResource(R.attr.colorSecondary)
            button.isEnabled = true
            holder.binding.textViewItemLessen.textSize = 14f
            val layoutParam = button.layoutParams as ViewGroup.MarginLayoutParams
            layoutParam.topMargin = 8 * density
        }

        holder.binding.textViewItemLessen.setOnClickListener {
            onClickLesson.onClickLessen(lessonModel)
        }

    }

    override fun getItemCount(): Int = listData.size

    inner class LessenViewHolder(var binding: ItemLessonButtonBinding) :
        RecyclerView.ViewHolder(binding.root)
}