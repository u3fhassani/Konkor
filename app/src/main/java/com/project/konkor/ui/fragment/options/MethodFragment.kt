package com.project.konkor.ui.fragment.options

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.MethodAdapter
import com.project.konkor.databinding.FragmentMethodBinding
import com.project.konkor.model.MethodModel
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.MethodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MethodFragment : Fragment(R.layout.fragment_method) {

    private lateinit var binding: FragmentMethodBinding
    private lateinit var methodAdapter: MethodAdapter
    private val methodViewModel: MethodViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMethodBinding.bind(view)

        setupRequest()

        methodAdapter = MethodAdapter()

        binding.recyclerViewMethod.apply {
            adapter = methodAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }


    }

    private fun setupDatabase() {

        methodViewModel.getMethodFromDB.observe(viewLifecycleOwner){

            if (!it.isNullOrEmpty()) {
                setupLoading(false)
                methodAdapter.setMethodList(it)
            }

        }

    }

    private fun setupRequest() {

        methodViewModel.getMethodFromServer.observe(viewLifecycleOwner) {
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
                    setupDatabase()
                    setupException(it.exception)
                }
            }
        }


    }

    private fun setupLoading(loading: Boolean) {
        binding.progressMethodLoading.apply {
            visibility = if (loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setupViewSuccess(data: List<MethodModel>) {

        methodAdapter.setMethodList(data)

    }

    private fun setupException(exception: Exception) {

        Log.e("exception", "setupException: $exception" )

        Snackbar.make(
            binding.coordinatorMethodRoot,
            "لطفا اینترنت خود را برسی کنید",
            Snackbar.LENGTH_LONG
        ).show()

    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Method List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle)
    }


}