package com.project.konkor.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.konkor.R
import com.project.konkor.adapter.CategoryAdapter
import com.project.konkor.databinding.FragmentCategoryBinding
import com.project.konkor.interfaces.OnClickManage
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.ui.activity.MainActivity
import com.project.konkor.util.DataGenerator
import com.project.konkor.util.DataState
import com.project.konkor.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category),
    OnClickManage.OnClickCategory {

    @Inject
    lateinit var sharedPrefAuth: SharedPreferencesAuth

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)


//        sharedPrefAuth.saveCategoryLessen("")

        categoryAdapter = CategoryAdapter(this)

        CoroutineScope(Main).launch {
            categoryAdapter.setListCat(DataGenerator.getCategoryList())
        }

        val gridLayoutManager = GridLayoutManager(context, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 4) 2 else 1
            }

        }


        binding.recyclerViewCategory.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }

        sendCategoryToServer()

    }

    private fun sendCategoryToServer() {
        authViewModel.category.observe(viewLifecycleOwner) {
            when (it) {
                DataState.Loading -> {
                    setupLoading(true)
                }
                is DataState.Success -> {
                    setupViewSuccess(it.data)
                    setupLoading(false)
                }
                is DataState.Error -> {
                    setupException(it.exception)
                    setupLoading(false)
                }
            }

        }
    }

    private fun setupViewSuccess(data: Boolean) {


        if (data) {
//            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun setupLoading(loading: Boolean) {
        binding.progressBarCategory.visibility =
            if (loading) {
                VISIBLE
            } else {
                GONE
            }
    }

    private fun setupException(exception: Exception) {
        Log.e("error", "setupException: ${exception.message}")

        Snackbar.make(
            binding.relativeLayoutCategoryRoot,
            "لطفا اینترنت خود را برسی کنید",
            Snackbar.LENGTH_LONG
        ).show()
    }


    override fun onClickCategory(nameCat: String, view: View) {
        sharedPrefAuth.saveCategoryLessen(nameCat)
        authViewModel.categoryMethod(nameCat)
    }


}


