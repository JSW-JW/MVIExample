package com.codingwithmitch.mviexample.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.mviexample.R
import com.codingwithmitch.mviexample.ui.DataStateListener
import com.codingwithmitch.mviexample.ui.main.state.MainStateEvent
import com.codingwithmitch.mviexample.ui.main.state.MainStateEvent.*
import com.codingwithmitch.mviexample.util.DataState
import java.lang.Exception

class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.dataState.removeObservers(viewLifecycleOwner)

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateHandler.onDataStateChange(dataState)
            println("DEBUG: DataState: ${dataState}")


            // Handle Data<T> (means 'in case of success response and retrieved proper data'.)
            dataState.data?.let {

                it.getContentIfNotHandled()?.let {

                    it.blogPosts?.let{
                        viewModel.setBlogListData(it)
                    }

                    it.user?.let {
                        viewModel.setUser(it)
                    }

                }

            }

            // Handle error
            dataState.message?.let {

            }

            // Handle loading
            dataState.loading.let {

            }

        })
        viewModel.viewState.removeObservers(viewLifecycleOwner)

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let {
                println("DEBUG: Setting blog posts to RecyclerView: ${it}")
            }

            viewState.user?.let {
                println("DEBUG: Setting user data: ${it}")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    override fun onAttach(context: Context) {  // when fragment is attached, this is invocated with the activity's context.
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener  // this will be passed to catch block if it's not implementing the DataStateListener
        }catch (e: ClassCastException) {
            println("$context should implement dataStateListener")
        }
    }



}