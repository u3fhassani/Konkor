package com.project.konkor.ui.fragment.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.konkor.R
import com.project.konkor.databinding.FragmentForgetPasswordBinding
import com.project.konkor.util.Constants.isEmailValid
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password), View.OnClickListener {

    private lateinit var binding: FragmentForgetPasswordBinding
    private val authViewModel: AuthViewModel by viewModels()

//    @Inject
//    lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgetPasswordBinding.bind(view)

        setupViews()
        setupRequest()

    }

    private fun setupViews() {

        binding.buttonForgetPasswordConfirm.setOnClickListener(this)

        binding.imageViewForgetPasswordBackPressed.setOnClickListener {
            it.findNavController().popBackStack()
        }

    }

    private fun setupRequest() {

        authViewModel.forgetPassword.observe(viewLifecycleOwner) {
            when (it) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupLoading(false)
                    setupViewSuccess(it.data)
                }
                is DataState.Error -> {
                    setupLoading(false)
                    setupException(it.exception)
                }
            }
        }

    }

    private fun setupLoading(loading: Boolean) {
        binding.lottieForgetPasswordLoading.visibility =
            if (loading) {
                VISIBLE
            } else {
                GONE
            }
    }

    private fun setupViewSuccess(data: Boolean) {

        binding.apply {

            if (data) {

                val lottieAnimation = lottieAnimationViewForgetPassword
                lottieAnimation.setAnimation(R.raw.email_send)
                lottieAnimation.repeatCount = 0
                lottieAnimation.playAnimation()

                textViewForgetPasswordDescription.visibility = VISIBLE

                buttonForgetPasswordConfirm.isClickable = false

            } else {

                textViewForgetPasswordDescription.visibility = GONE

                Snackbar.make(
                    binding.constraintLayoutForgetPasswordRoot,
                    "ارسال موفقیت آمیز نبود لطفا دوباره تلاش کنید .",
                    Snackbar.LENGTH_LONG
                ).show()


            }

        }

    }

    private fun setupException(exception: Exception) {

        Log.e("TAG", "setupException:  ${exception.message}")

        when (exception.message?.trim()) {
            "HTTP 404" -> {
                Snackbar.make(
                    binding.constraintLayoutForgetPasswordRoot,
                    "کسی با این ایمیل ثبت نام نکرده است",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            "HTTP 403" -> {
                Snackbar.make(
                    binding.constraintLayoutForgetPasswordRoot,
                    "ایمیل معتبر نیست .",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {
                Snackbar.make(
                    binding.constraintLayoutForgetPasswordRoot,
                    "لطفا اینترنت خود را برسی کنید",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }


    }

    override fun onClick(v: View) {

        binding.apply {

            val email = textInputEditTextForgetPassword.text.toString()

            if (email.isNotEmpty()) {
                if (isEmailValid(email)) {

                    authViewModel.forgetPassword(email)

                } else {
                    textInputEditTextForgetPassword.error = "ایمیل وارد شده معتبر نمیباشد"
                }
            } else {
                textInputEditTextForgetPassword.error = "ایمیل نمیتواند خالی باشد"
            }

        }

    }


}