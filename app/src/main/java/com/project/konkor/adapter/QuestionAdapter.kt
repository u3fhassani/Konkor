package com.project.konkor.adapter

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.agog.mathdisplay.MTMathView
import com.google.android.material.color.MaterialColors
import com.project.konkor.R
import com.project.konkor.databinding.ItemQuestionBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.QuestionModel
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants.FONT_BADR
import com.project.konkor.util.SetFonts
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class QuestionAdapter(
    private val onClickQuestionChoice: OnClickManage.OnClickQuestionChoice,
    private val setFonts: SetFonts,
    private val sharedPrefSetting: SharedPreferencesSetting
) :
    RecyclerView.Adapter<QuestionAdapter.MyViewHolder>() {

    private var listData: List<QuestionModel> = emptyList()
    private var trueAnswer: Boolean = false
    private var clickable: Boolean = false

    fun setTrueAnswer(trueAnswer: Boolean) {
        this.trueAnswer = trueAnswer
        notifyDataSetChanged()
    }

    fun setClickable(clickable: Boolean) {
        this.clickable = clickable
        notifyDataSetChanged()
    }

    fun setQuestionList(listData: List<QuestionModel>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionBinding.inflate(
            inflater,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listData.size

    inner class MyViewHolder(val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(questionModel: QuestionModel) {

            when (questionModel.lesson) {
                "language", "vocabulary", "reading", "grammar", "languageFunctions", "clozeTest", "orderOfWords" -> {
                    binding.itemQuestionRoot.layoutDirection = View.LAYOUT_DIRECTION_RTL
                }
                else -> {
                    binding.itemQuestionRoot.layoutDirection = View.LAYOUT_DIRECTION_LTR
                }
            }

            val questionLines = questionModel.question.split("#")

            binding.linearLayoutQuestionQuestion.removeAllViews()

            questionLines.forEach { line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty()) return@forEach
                if (trimmed.startsWith('$')) {
                    val latexContent = trimmed.drop(1)
                    val mathView = MTMathView(binding.root.context).apply {
                        latex = latexContent
                        fontSize = MTMathView.convertDpToPixel(17f)
                        textAlignment = MTMathView.MTTextAlignment.KMTTextAlignmentRight
                        textColor = MaterialColors.getColor(
                            binding.root.context,
                            com.google.android.material.R.attr.colorOnSurface,
                            ContextCompat.getColor(binding.root.context, R.color.black)
                        )
                    }
                    val params = ViewGroup.MarginLayoutParams(
                        MATCH_PARENT,
                        WRAP_CONTENT
                    ).apply {
                        topMargin = 24
                        bottomMargin = 24
                    }
                    binding.linearLayoutQuestionQuestion.addView(mathView, params)
                } else {
                    val textView = TextView(binding.root.context)
                    textView.text = trimmed
                    textView.setTextColor(
                        MaterialColors.getColor(
                            textView,
                            com.google.android.material.R.attr.colorOnSurface
                        )
                    )
                    textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

                    setFonts.setFontOnTextView(textView)
                    if (sharedPrefSetting.fontMode == FONT_BADR) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f,  binding.root.context.resources.displayMetrics), 1.0f)
                    } else {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6.0f,  binding.root.context.resources.displayMetrics), 1.0f)
                    }
                    binding.linearLayoutQuestionQuestion.addView(textView)
                }
            }

            binding.tvItemQuestionChoice1.text = questionModel.choice1.trim()
            binding.tvItemQuestionChoice2.text = questionModel.choice2.trim()
            binding.tvItemQuestionChoice3.text = questionModel.choice3.trim()
            binding.tvItemQuestionChoice4.text = questionModel.choice4.trim()
            binding.tvItemQuestionQuestionNumber.text = questionModel.questionNumber.toString()

            if (questionModel.imageUrlQuestion.isNullOrEmpty()) {
                binding.imageViewItemQuestion.isVisible = false
            } else {
                binding.imageViewItemQuestion.isVisible = true

                when (binding.root.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        Picasso.get().load(questionModel.imageUrlQuestion)
                            .into(binding.imageViewItemQuestion)
                    } // Night mode is not active, we're using the light theme
                    Configuration.UI_MODE_NIGHT_YES -> {
                        Picasso.get().load(questionModel.imageUrlQuestionNight)
                            .into(binding.imageViewItemQuestion)
                    } // Night mode is active, we're using dark theme
                    Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                        Picasso.get().load(questionModel.imageUrlQuestion)
                            .into(binding.imageViewItemQuestion)
                    }
                }

            }

        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val questionModel = listData[position]

        if (clickable) {
            holder.binding.tvItemQuestionChoice1.isEnabled = true
            holder.binding.tvItemQuestionChoice2.isEnabled = true
            holder.binding.tvItemQuestionChoice3.isEnabled = true
            holder.binding.tvItemQuestionChoice4.isEnabled = true
        } else {
            holder.binding.tvItemQuestionChoice1.isEnabled = false
            holder.binding.tvItemQuestionChoice2.isEnabled = false
            holder.binding.tvItemQuestionChoice3.isEnabled = false
            holder.binding.tvItemQuestionChoice4.isEnabled = false
        }


        if (trueAnswer) {
            showTrueAnswer(questionModel, holder.binding)
        } else {
            onClickChoice(questionModel, holder.binding)
        }

        holder.bind(questionModel)
    }


    private fun showTrueAnswer(questionModel: QuestionModel, binding: ItemQuestionBinding) {


        when (questionModel.answerTrueNumber) {

            1 -> {
                binding.tvItemQuestionChoice1.isSelected = true
            }
            2 -> {
                binding.tvItemQuestionChoice2.isSelected = true
            }
            3 -> {
                binding.tvItemQuestionChoice3.isSelected = true
            }
            4 -> {
                binding.tvItemQuestionChoice4.isSelected = true
            }

        }

        when (questionModel.choice) {

            0 -> {
                binding.tvItemQuestionChoice1.isActivated = false
                binding.tvItemQuestionChoice2.isActivated = false
                binding.tvItemQuestionChoice3.isActivated = false
                binding.tvItemQuestionChoice4.isActivated = false
            }
            1 -> {
                binding.tvItemQuestionChoice1.isActivated = true
            }
            2 -> {
                binding.tvItemQuestionChoice2.isActivated = true
            }
            3 -> {
                binding.tvItemQuestionChoice3.isActivated = true
            }
            4 -> {
                binding.tvItemQuestionChoice4.isActivated = true
            }


        }


    }

    private fun onClickChoice(questionModel: QuestionModel, binding: ItemQuestionBinding) {

        val textViewChoice1 = binding.tvItemQuestionChoice1
        val textViewChoice2 = binding.tvItemQuestionChoice2
        val textViewChoice3 = binding.tvItemQuestionChoice3
        val textViewChoice4 = binding.tvItemQuestionChoice4

        textViewChoice1.setOnClickListener {
            if (questionModel.choice == 1 && it.isActivated) {
                questionModel.choice = 0
                it.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            } else {
                questionModel.choice = 1
                it.isActivated = true
                textViewChoice2.isActivated = false
                textViewChoice3.isActivated = false
                textViewChoice4.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            }
        }
        textViewChoice2.setOnClickListener {
            if (questionModel.choice == 2 && it.isActivated) {
                questionModel.choice = 0
                it.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            } else {
                questionModel.choice = 2
                it.isActivated = true
                textViewChoice1.isActivated = false
                textViewChoice3.isActivated = false
                textViewChoice4.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)

            }
        }
        textViewChoice3.setOnClickListener {
            if (questionModel.choice == 3 && it.isActivated) {
                questionModel.choice = 0
                it.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            } else {
                questionModel.choice = 3
                it.isActivated = true
                textViewChoice1.isActivated = false
                textViewChoice2.isActivated = false
                textViewChoice4.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            }
        }
        textViewChoice4.setOnClickListener {
            if (questionModel.choice == 4 && it.isActivated) {
                questionModel.choice = 0
                it.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            } else {
                questionModel.choice = 4
                it.isActivated = true
                textViewChoice1.isActivated = false
                textViewChoice2.isActivated = false
                textViewChoice3.isActivated = false
                onClickQuestionChoice.setChoice(questionModel)
            }
        }

    }


}