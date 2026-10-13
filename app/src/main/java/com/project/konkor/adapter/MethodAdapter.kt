package com.project.konkor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.project.konkor.databinding.ItemMethodBinding
import com.project.konkor.model.MethodModel
import com.project.konkor.util.Constants

class MethodAdapter() :
    RecyclerView.Adapter<MethodAdapter.MethodViewHolder>() {

    private var listData = listOf<MethodModel>()

    fun setMethodList(listMethod: List<MethodModel>) {
        listData = listMethod
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMethodBinding.inflate(inflater, parent, false)
        return MethodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {

        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.count()

    inner class MethodViewHolder(val binding: ItemMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(methodModel: MethodModel) {
            binding.apply {

                textViewItemMethodTitle.text = methodModel.methodTitle
                textViewItemMethodDescription.text = methodModel.methodDescription


                lottieItemMethodAnimation.setAnimationFromUrl(/*methodModel.lottieAnimation)*/
                    methodModel.lottieAnimation.replace(
                        "localhost",
                        Constants.IP_ADDRESS
                    )
                )
                lottieItemMethodAnimation.repeatCount = LottieDrawable.INFINITE
                lottieItemMethodAnimation.playAnimation()

            }
        }

    }

}