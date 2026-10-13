package com.project.konkor.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.databinding.FragmentSignUpBinding
import com.project.konkor.model.AuthModel
import com.project.konkor.ui.activity.MainActivity
import com.project.konkor.util.Constants.isEmailValid
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var buttonSignUp: MaterialButton
    private val authViewModel: AuthViewModel by viewModels()

    private var TAG = "SignUpFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        buttonSignUp = binding.buttonSignUpCreate


        setupSignUp()
        setupView()


    }

    private fun setupView() {

        binding.editTextPassword.doOnTextChanged { _, _, _, count ->
            if (count > 0) {
                binding.textInputLayoutPassword.isErrorEnabled = false
            }
        }

        binding.textViewSignUpAboutUs.setOnClickListener {
            val bundle = bundleOf("auth" to 1)
            it.findNavController()
                .navigate(R.id.action_authFragment_to_aboutUsFragment2, bundle)
        }

        binding.textViewSignUpPrivacyPolicy.setOnClickListener {
            val bundle = bundleOf("auth" to 1)
            it.findNavController()
                .navigate(R.id.action_authFragment_to_privacyPolicyFragment2, bundle)
        }


        buttonSignUp.setOnClickListener {

            val firstName = binding.editTextName.text?.trim().toString()
            val lastName = binding.editTextFamily.text?.trim().toString()
            val email = binding.editTextEmail.text?.trim().toString()
            val password = binding.editTextPassword.text?.trim().toString()
            val repeatPassword = binding.editTextRepeatPassword.text?.trim().toString()




            if (firstName.isNotEmpty()) {
                if (lastName.isNotEmpty()) {
                    if (email.isNotEmpty()) {
                        if (isEmailValid(email)) {
                            if (password.isNotEmpty()) {
                                if (password.length >= 8) {
                                    if (repeatPassword.isNotEmpty()) {
                                        if (repeatPassword == password) {

                                            authViewModel.registerMethod(
                                                firstName,
                                                lastName,
                                                email,
                                                password
                                            )

                                        } else {
                                            Snackbar.make(
                                                binding.constraintLayoutSignUpRoot,
                                                "رمز عبور و تکرار آن باید برابر باشند",
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        binding.textInputLayoutRepeatPassword.error =
                                            "تکرار رمز عبور نمیتواند خالی باشد"
                                    }
                                } else {
                                    binding.textInputLayoutPassword.error =
                                        "رمز عبور باید بیشتر از ۸ کاراکتر باشد"
                                }
                            } else {
                                binding.textInputLayoutPassword.error =
                                    "رمز عبور نمیتواند خالی باشد"
                            }
                        } else {
                            binding.editTextEmail.error = "ایمیل شما معتبر نیست"
                        }
                    } else {
                        binding.editTextEmail.error = "ایمیل شما نمیتواند خالی باشد"
                    }
                } else {
                    binding.editTextFamily.error = "نام خانوادگی شما نمیتواند خالی باشد"
                }
            } else {
                binding.editTextName.error = "نام شما نمیتواند خالی باشد"
            }


        }


    }

    private fun setupSignUp() {

        authViewModel.register.observe(viewLifecycleOwner) {
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


        binding.progressSignUpLoading.apply {

            visibility = if (loading) {
                VISIBLE
            } else {
                GONE
            }

        }

    }

    private fun setupViewSuccess(authModel: AuthModel) {
        Log.d(TAG, "setupViewSuccess: ${authModel.firstName}")

        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SIGN_UP, null)

        if (authModel.category.isEmpty()) {
            val navDirections = AuthFragmentDirections.actionAuthFragmentToCategoryFragment()
            buttonSignUp.findNavController().navigate(navDirections)
        } else {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }


    }

    private fun setupException(exception: Exception) {
        Log.e(TAG, "setupException: ${exception.message}", exception)

        if (exception.message?.trim() == "HTTP 403 Forbidden") {
            Snackbar.make(
                binding.constraintLayoutSignUpRoot,
                "با این ایمیل قبلا ثبت نام شده",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                binding.constraintLayoutSignUpRoot,
                "به اینترنت دسترسی ندارید لطفا اینترنت خود را برسی کنید",
                Snackbar.LENGTH_LONG
            ).show()
        }

    }


}