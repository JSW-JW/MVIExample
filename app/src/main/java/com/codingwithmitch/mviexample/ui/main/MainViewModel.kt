package com.codingwithmitch.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codingwithmitch.mviexample.ui.main.state.MainStateEvent
import com.codingwithmitch.mviexample.ui.main.state.MainViewState
import com.codingwithmitch.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent : MutableLiveData<MainStateEvent> = MutableLiveData();
    private val _viewState : MutableLiveData<MainViewState> = MutableLiveData();

    val viewState : LiveData<MainViewState>
        get() = _viewState

    // exactly the same with this
    /* fun observeViewState() : LiveData<MainViewState> {
        return _viewState
    }   */

    val dataState : LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent) : LiveData<MainViewState> {
        when(stateEvent) {

            is MainStateEvent.GetBlogPostsEvent -> {
                AbsentLiveData.create<MainViewState>()
            }
            is MainStateEvent.GetUserEvent -> {
                AbsentLiveData.create<MainViewState>()
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create<MainViewState>()
            }
        }
        return AbsentLiveData.create<MainViewState>()
    }

    

}