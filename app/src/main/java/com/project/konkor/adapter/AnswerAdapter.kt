package com.project.konkor.adapter

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.agog.mathdisplay.MTMathView
import com.google.android.material.color.MaterialColors
import com.project.konkor.R
import com.project.konkor.databinding.ItemAnswerBinding
import com.project.konkor.model.QuestionModel
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants
import com.project.konkor.util.SetFonts
import com.squareup.picasso.Picasso

class AnswerAdapter(
    private val setFonts: SetFonts,
    private val sharedPrefSetting: SharedPreferencesSetting
) :
    RecyclerView.Adapter<AnswerAdapter.MyViewHolder>() {

    private var itemList: List<QuestionModel> = emptyList()


    @SuppressLint("NotifyDataSetChanged")
    fun setQuestionList(itemList: List<QuestionModel>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bindItem(itemList[position])

    }

    override fun getItemCount(): Int = itemList.count()

    inner class MyViewHolder(val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindItem(questionModel: QuestionModel) {


            val questionLines = questionModel.answerText.split("#")

            binding.linearLayoutItemAnswer.removeAllViews()

            questionLines.forEach {
                if (it[0] == '$') {
                    it.drop(1)
                    val mathView = MTMathView(binding.root.context).apply {
                        latex = it
                        fontSize = MTMathView.convertDpToPixel(16f)
                        textAlignment = MTMathView.MTTextAlignment.KMTTextAlignmentRight
                        textColor = MaterialColors.getColor(
                            binding.root.context,
                            com.google.android.material.R.attr.colorOnSurface,
                            ContextCompat.getColor(binding.root.context, R.color.black)
                        )
                    }
                    val params = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = 24
                        bottomMargin = 24
                    }
                    binding.linearLayoutItemAnswer.addView(mathView, params)
                } else {
                    val textView = TextView(binding.root.context)
                    textView.text = it
                    textView.setTextColor(
                        MaterialColors.getColor(
                            textView,
                            com.google.android.material.R.attr.colorOnSurface
                        )
                    )

                    setFonts.setFontOnTextView(textView)
                    if (sharedPrefSetting.fontMode == Constants.FONT_BADR) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                        textView.setLineSpacing(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                0f,
                                binding.root.context.resources.displayMetrics
                            ), 1.0f
                        )
                    } else {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        textView.setLineSpacing(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                6.0f,
                                binding.root.context.resources.displayMetrics
                            ), 1.0f
                        )
                    }
                    binding.linearLayoutItemAnswer.addView(textView)
                }
            }

            if (questionModel.imageUrlAnswer.isNullOrEmpty()) {
                binding.imageViewItemAnswer.isVisible = false
            } else {
                binding.imageViewItemAnswer.isVisible = true

                when (binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        Picasso.get().load(questionModel.imageUrlAnswer)
                            .into(binding.imageViewItemAnswer)
                    } // Night mode is not active, we're using the light theme
                    Configuration.UI_MODE_NIGHT_YES -> {
                        Picasso.get().load(questionModel.imageUrlAnswerNight)
                            .into(binding.imageViewItemAnswer)
                    } // Night mode is active, we're using dark theme
                    Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                        Picasso.get().load(questionModel.imageUrlAnswer)
                            .into(binding.imageViewItemAnswer)
                    }
                }

            }

            binding.tvItemAnswerQuestionNumber.text = questionModel.questionNumber.toString()
            binding.textViewItemAnswerTrueAnswerCount.text =
                questionModel.answerTrueNumber.toString()

        }


    }

}