package com.project.konkor.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemTargetBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.TargetModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TargetAdapter(
    private val onClickTarget: OnClickManage.OnClickTarget
) :
    RecyclerView.Adapter<TargetAdapter.MyViewHolder>() {

    private var listTarget: MutableList<TargetModel> = mutableListOf()


//    private val diffCallBack = object : DiffUtil.ItemCallback<TargetModel>() {
//        override fun areItemsTheSame(oldItem: TargetModel, newItem: TargetModel): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: TargetModel, newItem: TargetModel): Boolean {
////            return when {
////                oldItem.id != newItem.id -> false
////                oldItem.title != newItem.title -> false
////                oldItem.time != newItem.time -> false
////                oldItem.checkBox != newItem.checkBox -> false
////                else -> true
////            }
//            return oldItem.equals(newItem)
//        }
//
//    }


//    private val differ = AsyncListDiffer(this, diffCallBack)


    fun addListTarget(listTarget: List<TargetModel>) {
        this.listTarget.clear()
        this.listTarget.addAll(listTarget)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTargetBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val target = listTarget[position]


//        if (target.checkBox) {
//            holder.binding.editTextItemTarget.paintFlags =
//                holder.binding.editTextItemTarget.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//        } else {
//            holder.binding.editTextItemTarget.paintFlags =
//                holder.binding.editTextItemTarget.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//        }

        val editText = holder.binding.editTextItemTarget
        val imageViewEdit = holder.binding.imageViewItemTargetEdit
        val imageViewDelete = holder.binding.imageViewItemTargetDelete

        holder.binding.apply {
            editTextItemTarget.text = target.title
//            checkBoxItemTarget.isChecked = target.checkBox
//            textViewProfileTargetNumber.text = (holder.adapterPosition + 1).toString()
            textViewItemTargetDate.text = target.date
        }

        holder.binding.cardViewItemTargetRoot.isChecked = target.checkBox

        holder.binding.imageViewItemTargetChecked.setOnClickListener {
            target.checkBox = !target.checkBox
            onClickTarget.checkBoxClick(target)
            notifyItemChanged(position)
            holder.binding.constraintLayoutItemTargetEdit.visibility = GONE
        }

        imageViewDelete.setOnClickListener {

            listTarget.remove(target)
            if (listTarget.size < 3) {
                notifyDataSetChanged()
            } else {
                notifyItemRemoved(position)
            }
            holder.binding.constraintLayoutItemTargetEdit.visibility = GONE
            onClickTarget.removeItemTarget(target, listTarget.size)
        }

        imageViewEdit.setOnClickListener {

//            if (editText.isEnabled) {
//                imageViewEdit.setImageResource(R.drawable.ic_baseline_edit_24)
//                target.title = editText.text.toString()
//                editText.isEnabled = false
//            } else {
//                imageViewEdit.setImageResource(R.drawable.ic_baseline_done_24)
//                editText.isFocusable = true
//
//                val methodManager =
//                    holder.itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                methodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
//
//                editText.isEnabled = true
//            }

            CoroutineScope(Main).launch {
                delay(200)
                holder.binding.constraintLayoutItemTargetEdit.visibility = GONE
            }


            onClickTarget.editItemTarget(target, editText.isEnabled)
        }

        holder.binding.root.setOnClickListener {
            holder.binding.constraintLayoutItemTargetEdit.isGone = true
        }

        holder.binding.root.setOnLongClickListener {
            holder.binding.constraintLayoutItemTargetEdit.visibility = VISIBLE
            true
        }


//        holder.binding.checkBoxItemTarget.setOnCheckedChangeListener { _, isChecked ->
//
//            if (isChecked) {
//                holder.binding.editTextItemTarget.paintFlags =
//                    holder.binding.editTextItemTarget.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//
//            } else {
//                holder.binding.editTextItemTarget.paintFlags =
//                    holder.binding.editTextItemTarget.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//            }
//
//            target.checkBox = isChecked
//            onClickTarget.checkBoxClick(target, isChecked)
//        }


    }

    fun updateItem(targetModel: TargetModel) {
        val position = listTarget.indexOf(targetModel)
        notifyItemChanged(position)
    }

    fun addTarget(targetModel: TargetModel) {
//        listTarget.add(targetModel)
        listTarget.add(targetModel)
        notifyDataSetChanged()
    }

//    fun removeItemTarget(targetModel: TargetModel, position: Int) {
//        listTarget.removeAt(position)
//        notifyItemRemoved(position)
//    }


    override fun getItemCount(): Int = listTarget.size

    inner class MyViewHolder(val binding: ItemTargetBinding) :
        RecyclerView.ViewHolder(binding.root)


}