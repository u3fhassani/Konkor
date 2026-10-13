package com.project.konkor.ui.fragment.yearKonkor

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.QuestionAdapter
import com.project.konkor.databinding.FragmentQuestionBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.QuestionModel
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants.persianName
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.FavoriteStateEvent
import com.project.konkor.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class QuestionFragment : Fragment(R.layout.fragment_question),
    OnClickManage.OnClickQuestionChoice {


    private lateinit var binding: FragmentQuestionBinding
    private val viewModel: QuestionViewModel by activityViewModels()

    @Inject
    lateinit var sharedPrefSetting: SharedPreferencesSetting

    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var countDownTimer: CountDownTimer
    private var saveResult: Int = 0
    private var timeMS: Long = 0
    private var questionSize: Int = 0
    private var favoriteMode = false
    private val listTrueAnswer = mutableMapOf<String, Int>()
    private val listAnswerChoice = mutableMapOf<String, Int>()
    var timerJob: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionBinding.bind(view)

        setupView()
        getQuestionFromServer()

        timeMS = (viewModel.lessonModel!!.time * 60000).toLong()
        binding.buttonQuestionStart.setOnClickListener(this::onClickStart)
        binding.buttonQuestionEnd.setOnClickListener {
            onClickEnd()
        }
        binding.buttonQuestionResult.setOnClickListener {
            onClickResult()
        }
        binding.imageViewQuestionBackPressed.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageViewFavoriteQuestion.setOnClickListener {
            onClickFavorite(favoriteMode)
        }

        binding.failedConnection.buttonFailedConnection.setOnClickListener {
            viewModel.getQuestionFromNetwork()
        }


    }


    @SuppressLint("SetTextI18n")
    private fun setupView() {

        viewModel.favoriteExist.observe(viewLifecycleOwner) {
            favoriteMode = it
            if (it) {
                binding.imageViewFavoriteQuestion.setImageResource(R.drawable.ic_bookmark_48px)
            } else {
                binding.imageViewFavoriteQuestion.setImageResource(R.drawable.ic_bookmark_border_48px)
            }
        }

        val categoryPersianName = viewModel.lessonModel!!.category!!.persianName()


        questionAdapter = QuestionAdapter(this, viewModel.setFonts, sharedPrefSetting)

        binding.textViewQuestionTime.text = "${viewModel.lessonModel!!.time} دقیقه"

        binding.textViewQuestionCount.text = viewModel.lessonModel!!.questionCount.toString()

        val textToolbar =
            "${viewModel.lessonModel!!.lessonPersianName} $categoryPersianName سال ${viewModel.lessonModel!!.idYear}"

        binding.textViewQuestionToolbar.text = textToolbar

        binding.recyclerViewQuestion.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


    }


    private fun onClickFavorite(isChecked: Boolean) {
        if (!isChecked) {
            viewModel.favoriteState(FavoriteStateEvent.InsertFavorite)
            binding.imageViewFavoriteQuestion.setImageResource(R.drawable.ic_bookmark_48px)
        } else {
            viewModel.favoriteState(FavoriteStateEvent.DeleteFavorite)
            binding.imageViewFavoriteQuestion.setImageResource(R.drawable.ic_bookmark_border_48px)
        }

    }


    private fun getQuestionFromServer() {

        viewModel.questionFromNetwork.observe(viewLifecycleOwner) {
            when (it) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupLoading(false)
                    setupViewsQuestion(it.data)
                }
                is DataState.Error -> {
                    setupLoading(false)
                    setupException(it.exception)
                }
            }
        }

    }


    private fun manageNumber(questionModel: QuestionModel) {

        val textChoice1 = questionModel.choice1
        val textChoice2 = questionModel.choice2
        val textChoice3 = questionModel.choice3
        val textChoice4 = questionModel.choice4


        when (questionModel.lesson) {
            "language", "vocabulary", "reading", "grammar", "languageFunctions", "clozeTest", "orderOfWords" -> {
                questionModel.choice1 = "1) $textChoice1"
                questionModel.choice2 = "2) $textChoice2"
                questionModel.choice3 = "3) $textChoice3"
                questionModel.choice4 = "4) $textChoice4"
            }
            else -> {
                questionModel.choice1 = "۱) $textChoice1"
                questionModel.choice2 = "۲) $textChoice2"
                questionModel.choice3 = "۳) $textChoice3"
                questionModel.choice4 = "۴) $textChoice4"
            }
        }
    }

    private fun setupLoading(loading: Boolean) {

        binding.failedConnection.buttonFailedConnection.isClickable = false

        binding.lottieQuestionLoading.visibility =
            if (loading) {
                VISIBLE
            } else {
                GONE
            }


    }

    private fun setupViewsQuestion(questions: List<QuestionModel>) {

        binding.failedConnection.constraintNetworkFailedConnection.visibility = GONE
        binding.failedConnection.buttonFailedConnection.isClickable = true

        lifecycleScope.launch(coroutineExceptionHandler) {
            withContext(IO) {
                questions.forEach { questionModel: QuestionModel ->
                    manageNumber(questionModel)
                }
            }
            binding.buttonQuestionStart.visibility = VISIBLE
            binding.viewQuestionLineLeft.visibility = VISIBLE
            binding.viewQuestionLineRight.visibility = VISIBLE
            questionAdapter.setQuestionList(questions)
            questionSize = questions.size
        }

    }

    private fun setupException(exception: Exception) {
        Log.e("TAG", "setupException: ${exception.message}")

        when (exception.message?.trim()) {
            "HTTP 404" -> {
                Snackbar.make(
                    binding.constraintLayoutQuestionRoot,
                    "سوالات مورد نظر هنوز ثبت نشده اند",
                    Snackbar.LENGTH_LONG
                ).show()

                binding.failedConnection.constraintNetworkFailedConnection.visibility = GONE
                binding.failedConnection.buttonFailedConnection.isClickable = true
            }
            else -> {
                Snackbar.make(
                    binding.constraintLayoutQuestionRoot,
                    "لطفا اینترنت خود را برسی کنید",
                    Snackbar.LENGTH_LONG
                ).show()

                binding.failedConnection.constraintNetworkFailedConnection.visibility = VISIBLE
                binding.failedConnection.buttonFailedConnection.isClickable = true

            }

        }


    }

    override fun setChoice(questionModel: QuestionModel) {

        if (questionModel.choice == 0) {
            listTrueAnswer.remove(questionModel.id)
            listAnswerChoice.remove(questionModel.id)
        } else {
            listAnswerChoice[questionModel.id] = questionModel.choice
            listTrueAnswer[questionModel.id] = questionModel.answerTrueNumber
        }

    }

    private fun onClickStart(view: View) {
        questionAdapter.setClickable(true)

        listAnswerChoice.clear()
        listTrueAnswer.clear()

        binding.textViewQuestionToolbar.visibility = GONE
        binding.tvQuestionTime.visibility = GONE
        binding.tvQuestionCount.visibility = GONE
        binding.textViewQuestionTime.visibility = GONE
        binding.textViewQuestionCount.visibility = GONE

        binding.buttonQuestionResult.visibility = GONE

        view.visibility = GONE
        binding.viewQuestionLineLeft.visibility = GONE
        binding.viewQuestionLineRight.visibility = GONE

        binding.imageViewQuestionBackPressed.visibility = GONE

        binding.textViewQuestionTimer.visibility = VISIBLE

        binding.buttonQuestionEnd.visibility = VISIBLE

        timerJob = lifecycleScope.launch(coroutineExceptionHandler) {

            countDownTimer = object : CountDownTimer(timeMS, 1000) {
                override fun onTick(time: Long) {

                    val minute = (time / 60000)
                    val second = (time / 1000) % 60

                    val timeFormat = String.format("%02d:%02d", minute, second)
                    binding.textViewQuestionTimer.text = timeFormat


                }

                override fun onFinish() {
                    saveResult = 1

                    questionAdapter.setTrueAnswer(true)
                    questionAdapter.setClickable(false)

                    binding.textViewQuestionTimer.visibility = GONE
                    binding.textViewQuestionToolbar.visibility = VISIBLE

//                    binding.buttonQuestionStart.visibility = VISIBLE
//                    binding.viewQuestionLineLeft.visibility = VISIBLE
//                    binding.viewQuestionLineRight.visibility = VISIBLE

                    binding.tvQuestionTime.visibility = VISIBLE
                    binding.tvQuestionCount.visibility = VISIBLE
                    binding.textViewQuestionTime.visibility = VISIBLE
                    binding.textViewQuestionCount.visibility = VISIBLE
                    binding.buttonQuestionEnd.visibility = GONE

                    val layoutParam =
                        binding.recyclerViewQuestion.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParam.bottomMargin = 0

                    binding.buttonQuestionResult.visibility = VISIBLE
                    binding.imageViewQuestionBackPressed.visibility = VISIBLE

                    setupResult()

                }

            }.start()

        }


    }

    fun onClickEnd() {
        saveResult = 1

        questionAdapter.setTrueAnswer(true)
        questionAdapter.setClickable(false)

        countDownTimer.cancel()

        binding.textViewQuestionTimer.visibility = GONE
        binding.textViewQuestionToolbar.visibility = VISIBLE

//        binding.buttonQuestionStart.visibility = VISIBLE
//        binding.viewQuestionLineLeft.visibility = VISIBLE
//        binding.viewQuestionLineRight.visibility = VISIBLE

        binding.tvQuestionTime.visibility = VISIBLE
        binding.tvQuestionCount.visibility = VISIBLE
        binding.textViewQuestionTime.visibility = VISIBLE
        binding.textViewQuestionCount.visibility = VISIBLE

        binding.buttonQuestionResult.visibility = VISIBLE

        binding.buttonQuestionEnd.visibility = GONE

        val layoutParam = binding.recyclerViewQuestion.layoutParams as ViewGroup.MarginLayoutParams
        layoutParam.bottomMargin = 0

        binding.imageViewQuestionBackPressed.visibility = VISIBLE

        setupResult()

    }


    private fun onClickResult() {
        saveResult = -1
        setupResult()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("exception", "${throwable.message}")
    }

    private fun setupResult() {

        lifecycleScope.launch(coroutineExceptionHandler) {

            val questionCount = questionSize //lessonModel.questionCount
            val questionAnswerChoice = listAnswerChoice.count()

            val notAnswer = questionCount - questionAnswerChoice

            var trueAnswer = 0f
            var falseAnswer = 0f

            listTrueAnswer.forEach { (position, answer) ->
                if (listAnswerChoice[position] == answer) {
                    trueAnswer += 1
                } else {
                    falseAnswer += 1
                }
            }

            val responseRate = if (questionCount > 0) {
                (((3 * trueAnswer - falseAnswer) * 100) / (questionCount * 3))
            } else {
                0f
            }

            val decimal = BigDecimal(responseRate.toDouble()).setScale(2, RoundingMode.HALF_EVEN)

            alertDialog(
                "$decimal",
                questionAnswerChoice,
                notAnswer,
                trueAnswer.toInt(),
                falseAnswer.toInt()
            )


            if (saveResult == 1) {
                viewModel.insertResult(
                    "$decimal",
                    trueAnswer.toInt(),
                    falseAnswer.toInt(),
                    notAnswer
                )
            }

            timerJob = null
        }

    }


    private fun alertDialog(
        responseRate: String,
        questionAnswerChoice: Int,
        notAnswer: Int,
        trueAnswer: Int,
        falseAnswer: Int
    ) {

        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.dialog_question)
            .cancelable(false)
            .positiveButton(text = "مشاهده شد") {
                it.dismiss()
            }

        dialog.getCustomView().apply {

            findViewById<TextView>(R.id.textView_dialog_allAnswer_count).text =
                "$questionAnswerChoice"
            findViewById<TextView>(R.id.textView_dialog_notAnswer_count).text = "$notAnswer"
            findViewById<TextView>(R.id.textView_dialog_trueAnswer_count).text = "$trueAnswer"
            findViewById<TextView>(R.id.textView_dialog_falseAnswer_count).text = "$falseAnswer"
            findViewById<TextView>(R.id.textView_dialog_responseRate).text = responseRate
            findViewById<TextView>(R.id.textView_dialog_dismiss).setOnClickListener {
                dialog.dismiss()
            }

        }


//        dialog.cornerRadius(res = R.dimen.materialDialogCorner)


        (dialog.window!!.decorView as ViewGroup)
            .getChildAt(0).startAnimation(
                AnimationUtils.loadAnimation(
                    context, R.anim.menu_show_up_center
                )
            )



        dialog.show()


    }

    override fun onResume() {
        super.onResume()

        viewModel.favoriteState(FavoriteStateEvent.ExistFavorite)
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Questions List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun isTimerRunning(): Boolean {
        return timerJob != null
    }

    override fun onDestroyView() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        timerJob?.cancel()
        timerJob = null
        super.onDestroyView()
    }

}