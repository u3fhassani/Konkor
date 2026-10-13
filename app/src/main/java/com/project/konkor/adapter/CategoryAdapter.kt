package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.konkor.databinding.ItemCategoryButtonBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.CategoryModel

class CategoryAdapter(
    private val onClickCategory: OnClickManage.OnClickCategory
) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private var listData = emptyList<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCategoryButtonBinding.inflate(inflater, parent, false)
        return MyViewHolder(view)
    }

    fun setListCat(listData: List<CategoryModel>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val catModel = listData[position]

        holder.binding.apply {
            if (catModel.nameCat == "ensani") {
                imageViewItemCategory.visibility = View.GONE
                textViewItemCategoryShear.visibility = View.VISIBLE
            } else {
                imageViewItemCategory.setImageResource(catModel.imageResource)
            }
            textViewItemCategory.text = catModel.persianName
        }

        holder.itemView.setOnClickListener {
            onClickCategory.onClickCategory(catModel.nameCat, it)
        }

    }

    override fun getItemCount(): Int = listData.size

    class MyViewHolder(val binding: ItemCategoryButtonBinding) :
        RecyclerView.ViewHolder(binding.root)
}