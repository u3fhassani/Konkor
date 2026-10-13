package com.project.konkor.ui.fragment.result

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.YearResultAdapter
import com.project.konkor.databinding.FragmentResultBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.ResultModel
import com.project.konkor.model.YearModel
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.ResultViewModel
import com.project.konkor.viewmodel.YearViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ResultFragment : Fragment(R.layout.fragment_result), OnClickManage.OnClickYearResult {

    @Inject
    lateinit var authPref: SharedPreferencesAuth

    private lateinit var binding: FragmentResultBinding
    private val yearViewModel: YearViewModel by activityViewModels()
    private val resultViewModel: ResultViewModel by viewModels()
    private lateinit var yearResultAdapter: YearResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultBinding.bind(view)

        yearResultAdapter = YearResultAdapter(this)
        resultViewModel.resultByCategory(authPref.categoryLesson)

        binding.failedConnection.buttonFailedConnection.setOnClickListener {
            yearViewModel.getYearData()
        }


        binding.recyclerViewResult.apply {
            setHasFixedSize(true)
            adapter = yearResultAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }


        resultViewModel.resultByCategory.observe(viewLifecycleOwner) { listResultModel ->
            yearViewModel.yearList.observe(viewLifecycleOwner) {

                when (it) {
                    DataState.Loading -> {
                        binding.failedConnection.buttonFailedConnection.isClickable = false
                        setupLoading(true)
                    }
                    is DataState.Error -> {
                        binding.failedConnection.constraintNetworkFailedConnection.visibility = VISIBLE
                        binding.failedConnection.buttonFailedConnection.isClickable = true
                        setupLoading(false)
                    }
                    is DataState.Success -> {
                        setupLoading(false)
                        binding.failedConnection.constraintNetworkFailedConnection.visibility = GONE
                        binding.failedConnection.buttonFailedConnection.isClickable = true
                        val listResult = it.data.f(listResultModel)
                        if (listResult.isEmpty()) {
                            resultEmpty(true)
                        } else {
                            resultEmpty(false)
                        }

                        yearResultAdapter.setYearList(listResult)
                    }
                }

            }
        }


    }

    private fun setupLoading(isLoading: Boolean) {
        binding.progressResult.visibility = if (isLoading) VISIBLE else GONE
    }

    private fun resultEmpty(isEmpty: Boolean) {
        binding.lottieResultEmptyState.visibility = if (isEmpty) VISIBLE else GONE
    }

    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Result")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

//    private fun listExist(
//        listResultModel: List<ResultModel>,
//        listYearModel: List<YearModel>
//    ): List<YearModel> {
//        return listYearModel.filter { yearModel ->
//            listResultModel.map { it.year.toString() }.contains(yearModel.year)
//        }
//    }


    private fun List<YearModel>.f(listResultModel: List<ResultModel>) = filter { m ->
        listResultModel.any {
            it.year == m.year &&
                    it.isNewSystem == m.isNewSystem &&
                    it.isAbroad == m.isAbroad
        }
    }

    override fun onClickYearResult(yearModel: YearModel) {
        yearModel.category = authPref.categoryLesson

        val navDirection =
            ResultFragmentDirections.actionResultFragmentToResponseFragment(yearModel)
        binding.recyclerViewResult.findNavController().navigate(navDirection)

    }


}