package com.project.konkor.ui.fragment.yearKonkor


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.project.konkor.R
import com.project.konkor.adapter.YearAdapter
import com.project.konkor.databinding.FragmentYearKonkorBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.model.YearModel
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.YearViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class YearKonkorFragment : Fragment(R.layout.fragment_year_konkor), OnClickManage.OnClickYear {

    private lateinit var binding: FragmentYearKonkorBinding
    private val yearViewModel: YearViewModel by activityViewModels()
    private lateinit var yearAdapter: YearAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentYearKonkorBinding.bind(view)

        setupRequest()
//        yearViewModel.getYearData()
        binding.failedConnection.buttonFailedConnection.setOnClickListener {
            yearViewModel.getYearData()
        }

    }

    private fun setupRequest() {
        yearViewModel.yearList.observe(viewLifecycleOwner) {
            when (it) {
                DataState.Loading -> {
                    binding.failedConnection.buttonFailedConnection.isClickable = false
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

    private fun setupLoading(isLoading: Boolean) {
        binding.progressYear.visibility = if (isLoading) VISIBLE else GONE
    }

    private fun setupSuccess(listYear: List<YearModel>) {

        binding.failedConnection.constraintNetworkFailedConnection.visibility = GONE
        binding.failedConnection.buttonFailedConnection.isClickable = true

        yearAdapter = YearAdapter(this,listYear)

        val gridLayoutManager = GridLayoutManager(context, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0 || position % 3 == 0) {
                    2
                } else if (listYear.size % 3 == 2) {
                    2
                } else {
                    1
                }

            }

        }

        binding.recyclerViewYear.apply {
            adapter = yearAdapter
            Log.d("mainActivity", "setupRecyclerView: ")
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }


    }

    private fun setupException(exception: Exception) {
        when (exception.message?.trim()) {
            "HTTP 404" -> {
                Toast.makeText(requireContext(), "محتوای خواسته شده پیدا نشد .", Toast.LENGTH_SHORT).show()
            }
            else -> {
                binding.failedConnection.constraintNetworkFailedConnection.visibility = VISIBLE
                binding.failedConnection.buttonFailedConnection.isClickable = true
            }
        }
    }


    override fun onClickYear(yearModel: YearModel, position: Int) {
        yearModel.category = yearViewModel.sharedPrefAuth.categoryLesson
        val navDirection =
            YearKonkorFragmentDirections.actionYearKonkorFragmentToLessonFragment(yearModel)
        binding.recyclerViewYear.findNavController().navigate(navDirection)

    }


    override fun onResume() {
        super.onResume()
        val bundle = bundleOf()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Year List")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.javaClass.simpleName)
        FirebaseAnalytics.getInstance(requireContext())
            .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle)
    }


}

