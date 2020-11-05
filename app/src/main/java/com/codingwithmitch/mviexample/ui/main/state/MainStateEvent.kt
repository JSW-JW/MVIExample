package com.codingwithmitch.mviexample.ui.main.state

sealed class MainStateEvent {

    class GetBlogPostsEvent() : MainStateEvent()

    class GetUserEvent(
        val userId : Int
    ) : MainStateEvent()

    class None : MainStateEvent()

}