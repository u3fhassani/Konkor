package com.project.konkor.ui.fragment.result

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.ResponseAdapter
import com.project.konkor.databinding.FragmentResponseBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.ResultModel
import com.project.konkor.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ResponseFragment : Fragment(R.layout.fragment_response), OnClickManage.OnClickResponse {

    private lateinit var binding: FragmentResponseBinding
    private val resultViewModel: ResultViewModel by viewModels()
    private val args: ResponseFragmentArgs by navArgs()
    private lateinit var responseAdapter: ResponseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResponseBinding.bind(view)

        resultViewModel.resultByCategoryAndYear(
            args.yearModel.category!!,
            args.yearModel.year
        )

        val linearLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, true)
        linearLayoutManager.stackFromEnd = true

        responseAdapter = ResponseAdapter(this)

        binding.recyclerViewResponse.apply {
            adapter = responseAdapter
            layoutManager = linearLayoutManager
        }


        resultViewModel.resultByCategoryAndYear.observe(viewLifecycleOwner) {
            responseAdapter.setListResponse(it)
        }


    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Response List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle)
    }

    override fun onClickResponse(resultModel: ResultModel) {


    }

    override fun onLongClickResponse(resultModel: ResultModel) {

    }

}