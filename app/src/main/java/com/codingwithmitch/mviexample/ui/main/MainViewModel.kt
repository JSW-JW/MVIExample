package com.codingwithmitch.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithmitch.mviexample.ui.main.state.MainViewState

class MainViewModel : ViewModel() {

    private val _viewState : MutableLiveData<MainViewState> = MutableLiveData();

    val viewState : LiveData<MainViewState>
        get() = _viewState

    // exactly the same with this
    /* fun observeViewState() : LiveData<MainViewState> {
        return _viewState
    }   */

    
}