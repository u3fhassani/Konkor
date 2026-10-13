package com.project.konkor.ui.fragment.yearKonkor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.AnswerAdapter
import com.project.konkor.databinding.FragmentAnswerBinding
import com.project.konkor.model.QuestionModel
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants.persianName
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AnswerFragment : Fragment(R.layout.fragment_answer) {


    private lateinit var binding: FragmentAnswerBinding
    private val viewModel: QuestionViewModel by activityViewModels()
    private lateinit var answerAdapter: AnswerAdapter
    @Inject
    lateinit var sharedPrefSetting: SharedPreferencesSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnswerBinding.bind(view)

        setupViews()
        getQuestionFromServer()

        binding.failedConnection.buttonFailedConnection.setOnClickListener {
            viewModel.getQuestionFromNetwork()
        }

    }

    private fun setupViews() {

        answerAdapter = AnswerAdapter(viewModel.setFonts,sharedPrefSetting)

        val textToolbar =
            "${viewModel.lessonModel!!.lessonPersianName} ${viewModel.lessonModel!!.category?.persianName()} سال ${viewModel.lessonModel!!.idYear}"

        binding.textViewToolbarTitleAnswer.text = textToolbar

        binding.recyclerViewAnswer.apply {
            adapter = answerAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }

        binding.imageViewAnswerBackPressed.setOnClickListener {
            requireActivity().onBackPressed()
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
                    setupViewsAnswer(it.data)
                }
                is DataState.Error -> {
                    setupLoading(false)
                    setupException(it.exception)
                }
            }
        }

    }

    private fun setupLoading(loading: Boolean) {

        binding.failedConnection.buttonFailedConnection.isClickable = false

        binding.progressAnswerLoading.visibility =
            if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }

    }

    private fun setupViewsAnswer(questionModel: List<QuestionModel>) {

        binding.failedConnection.constraintNetworkFailedConnection.visibility = View.GONE
        binding.failedConnection.buttonFailedConnection.isClickable = true

        lifecycleScope.launch {
            answerAdapter.setQuestionList(questionModel)
        }

    }

    private fun setupException(exception: Exception) {
        Log.e("TAG", "setupException: ${exception.message}")

        when (exception.message?.trim()) {
            "HTTP 404" -> {
                Snackbar.make(
                    binding.relativeLayoutAnswerRoot,
                    "سوالات مورد نظر هنوز ثبت نشده اند",
                    Snackbar.LENGTH_LONG
                ).show()

                binding.failedConnection.constraintNetworkFailedConnection.visibility = View.GONE
                binding.failedConnection.buttonFailedConnection.isClickable = true
            }
            else -> {
                Snackbar.make(
                    binding.relativeLayoutAnswerRoot,
                    "لطفا اینترنت خود را برسی کنید",
                    Snackbar.LENGTH_LONG
                ).show()

                binding.failedConnection.constraintNetworkFailedConnection.visibility = View.VISIBLE
                binding.failedConnection.buttonFailedConnection.isClickable = true

            }

        }


    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Answer List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


}