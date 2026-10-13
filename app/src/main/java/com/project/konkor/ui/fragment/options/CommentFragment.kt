package com.project.konkor.ui.fragment.options

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.project.konkor.R
import com.project.konkor.databinding.FragmentCommentBinding
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment(R.layout.fragment_comment) {

    private lateinit var binding: FragmentCommentBinding
    private val commentViewModel: CommentViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentBinding.bind(view)



        setupView()
        setupViewSendComment()

    }

    private fun setupViewSendComment() {
        commentViewModel.responseComment.observe(viewLifecycleOwner){
            when (it) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupLoading(false)
                    setupSuccess(it.data)
                }
                is DataState.Error -> {
                    setupLoading(false)
                    setupException(it.exception)
                }
            }
        }
    }

    private fun setupLoading(loading: Boolean) {
        binding.progressCommentLoading.visibility =
            if (loading) {
                VISIBLE
            } else {
                GONE
            }
    }

    private fun setupSuccess(data: Boolean) {

        if (data) {
            Snackbar.make(
                binding.constraintLayoutCommentRoot,
                "نظرات پیشنهادات شما با موفقیت ثبت شد .",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                binding.constraintLayoutCommentRoot,
                "ارسال نظر با مشکل روبرو شد لطفاً دوباره امتحان کنید",
                Snackbar.LENGTH_LONG
            ).show()
        }


    }

    private fun setupException(exception: Exception) {
        when (exception.message?.trim()) {
            "HTTP 404" -> {
                Snackbar.make(
                    binding.constraintLayoutCommentRoot,
                    "کسی با این ایمیل ثبت نام نکرده است",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else ->{
                Snackbar.make(
                    binding.constraintLayoutCommentRoot,
                    "لطفا اینترنت خود را برسی کنید",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupView() {

        binding.buttonCommentConfirm.setOnClickListener(this::onClickConfirmComment)

    }


    private fun onClickConfirmComment(view: View) {
        view as MaterialButton

        val comment = binding.editTextComment.text.toString()

        binding.editTextComment.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutComment.isErrorEnabled = false
            }
        }

        if (comment.isNotEmpty()) {
            commentViewModel.responseComment(comment)
        } else {
            binding.textInputLayoutComment.error = "لطفأ نظر یا پیشنهاد خود را وارد کنید ."
        }

    }


}