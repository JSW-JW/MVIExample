package com.codingwithmitch.mviexample.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codingwithmitch.mviexample.R
import com.codingwithmitch.mviexample.model.BlogPost
import com.codingwithmitch.mviexample.model.User
import com.codingwithmitch.mviexample.ui.DataStateListener
import com.codingwithmitch.mviexample.ui.main.state.MainStateEvent
import com.codingwithmitch.mviexample.ui.main.state.MainStateEvent.*
import com.codingwithmitch.mviexample.util.DataState
import com.codingwithmitch.mviexample.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Exception

class MainFragment : Fragment(), BlogListAdapter.Interaction {

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: $position")
        println("DEBUG: $item")
    }

    private val TAG = "MainFragment"

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    lateinit var blogListAdapter: BlogListAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            layoutManager = LinearLayoutManager(activity)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter // == recycler_view.setAdapter(blogListAdapter)
        }

    }

    private fun subscribeObservers() {
        viewModel.dataState.removeObservers(viewLifecycleOwner)

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateHandler.onDataStateChange(dataState)
            println("DEBUG: DataState: ${dataState}")


            // Handle Data<T> (means 'in case of success response and retrieved proper data'.)
            dataState.data?.let {

                it.getContentIfNotHandled()?.let {

                    it.blogPosts?.let {
                        viewModel.setBlogListData(it)
                    }

                    it.user?.let {
                        viewModel.setUser(it)
                    }

                }

            }

        })
        viewModel.viewState.removeObservers(viewLifecycleOwner)

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let { list ->
                println("DEBUG: Setting blog posts to RecyclerView: ${list}")
                blogListAdapter.submitList(list)
            }

            viewState.user?.let { user ->
                println("DEBUG: Setting user data: ${user}")
                setUserProperties(user)

            }
        })

    }

    private fun setUserProperties(user: User) {
        activity?.let { context ->
            Glide.with(context)
                .load(user.image)
                .into(image)

            email.text = user.email
            username.text = user.username
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
            dataStateHandler =
                context as DataStateListener  // this will be passed to catch block if it's not implementing the DataStateListener
        } catch (e: ClassCastException) {
            println("$context should implement dataStateListener")  // MainActivity should implement dataStateListener
        }
    }


}