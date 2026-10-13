package com.project.konkor.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.databinding.FragmentSignInBinding
import com.project.konkor.model.AuthModel
import com.project.konkor.ui.activity.MainActivity
import com.project.konkor.util.Constants.isEmailValid
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.AuthViewModel
import com.project.konkor.viewmodel.SignInStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var buttonSignIn: MaterialButton
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        buttonSignIn = binding.buttonSignIn

        setupAnimation()
        setupSignIn()
        setupView()


    }

    private fun setupView() {


        binding.editTextSignInPassword.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutSignInPassword.isErrorEnabled = false
            }
        }


        buttonSignIn.setOnClickListener {


            val email = binding.editTextSignInEmail.text.toString().trim()
            val password = binding.editTextSignInPassword.text.toString().trim()

            if (email.isNotEmpty()) {
                if (isEmailValid(email)) {
                    if (password.isNotEmpty()) {

                        authViewModel.signInMethod(email, password, SignInStateEvent.SignInEvents)

                    } else {
                        binding.textInputLayoutSignInPassword.error = "رمز عبور نمیتواند خالی باشد"
                    }
                } else {
                    binding.editTextSignInEmail.error = "ایمیل معتبر نیست"
                }
            } else {
                binding.editTextSignInEmail.error = "ایمیل نمیتواند خالی باشد"
            }


        }



        binding.textViewSignInForgetPassword.setOnClickListener {

//            materialDialog()
            val navDirection = AuthFragmentDirections.actionAuthFragmentToForgetPasswordFragment()
            it.findNavController().navigate(navDirection)

        }

    }

//    private fun materialDialog() {
//
//
//        val dialog = MaterialDialog(requireContext())
//            .noAutoDismiss()
//            .customView(R.layout.dialog_forget_password)
//
//
//        dialog.getCustomView().apply {
//
//
//
//
//
//        }
//
//
//        dialog.cornerRadius(res = R.dimen.materialDialogCorner)
//
//
//        (dialog.window!!.decorView as ViewGroup)
//            .getChildAt(0).startAnimation(
//                AnimationUtils.loadAnimation(
//                    context, R.anim.menu_show_up_center
//                )
//            )
//
//
//        dialog.show()
//
//
//    }

    private fun setupSignIn() {


        authViewModel.signIn.observe(viewLifecycleOwner) {
            when (it) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupLoading(false)
                    setupViewSuccess(it.data)
                }
                is DataState.Error -> {
                    setupException(it.exception)
                    setupLoading(false)
                }
            }
        }


    }

    private fun setupLoading(loading: Boolean) {
        binding.progressBarSignInLoading.apply {
            visibility = if (loading) {
                VISIBLE
            } else {
                GONE
            }
        }
    }


    private fun setupViewSuccess(authModel: AuthModel) {

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.LOGIN, null)


        if (authModel.category.isEmpty()) {
            val navDirections = AuthFragmentDirections.actionAuthFragmentToCategoryFragment()
            buttonSignIn.findNavController().navigate(navDirections)
        } else {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun setupException(exception: Exception) {
        Log.e("TAG", "setupException:  ${exception.localizedMessage}")
        Log.e("TAG", "setupException12:  ${exception}")

        when (exception.message?.trim()) {
            "HTTP 404 Not Found" -> {
                Snackbar.make(
                    binding.constraintLayoutSignInRoot,
                    "کسی با این ایمیل ثبت نام نکرده است",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            "HTTP 403 Forbidden" -> {
                Snackbar.make(
                    binding.constraintLayoutSignInRoot,
                    "رمز ورود شما اشتباه است",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {
                Snackbar.make(
                    binding.constraintLayoutSignInRoot,
                    "لطفا اینترنت خود را برسی کنید",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

    }

    private fun setupAnimation() {

        binding.textInputLayoutSignInEmail.apply {
            translationX = 800f
            alpha = 0f
            animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(300).start()
        }

        binding.textInputLayoutSignInPassword.apply {
            translationX = 800f
            alpha = 0f
            animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500).start()
        }

        binding.textViewSignInForgetPassword.apply {
            translationX = 800f
            alpha = 0f
            animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(500).start()
        }

        binding.buttonSignIn.apply {
            translationX = 800f
            alpha = 0f
            animate().translationX(0f).alpha(1f).setDuration(800).setStartDelay(700).start()
        }


    }


}