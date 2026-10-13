package com.project.konkor.repository

import com.project.konkor.model.YearModel
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.mapper.YearNetworkMapper
import com.project.konkor.util.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YearRepository
@Inject
constructor(
    private val retrofit: ApiInterface,
    private val yearNetworkMapper: YearNetworkMapper
){


    fun getYear() = flow<DataState<List<YearModel>>> {
        emit(DataState.Loading)
        try {
            val entities = retrofit.getYears()
            val yearModels = yearNetworkMapper.mapFromEntityList(entities)

            emit(DataState.Success(yearModels))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }



}