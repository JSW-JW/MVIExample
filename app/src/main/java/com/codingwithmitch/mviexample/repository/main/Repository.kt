package com.codingwithmitch.mviexample.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.codingwithmitch.mviexample.api.MyRetrofitBuilder
import com.codingwithmitch.mviexample.ui.main.state.MainViewState
import com.codingwithmitch.mviexample.util.ApiEmptyResponse
import com.codingwithmitch.mviexample.util.ApiErrorResponse
import com.codingwithmitch.mviexample.util.ApiSuccessResponse
import com.codingwithmitch.mviexample.util.DataState

object Repository {

    fun getBlogPosts() : LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object: LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(apiResponse.body)
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )   // TODO : handle error
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "Http 204. Returned NOTHING!"
                                )  // TODO : handle empty/error?
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId : String) : LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object: LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(user = apiResponse.body)
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )   // TODO : handle error
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "Http 204. Returned NOTHING!"
                                ) // TODO : handle empty/error?
                            }
                        }
                    }
                }
            }
    }

}