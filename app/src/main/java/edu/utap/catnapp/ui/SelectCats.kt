package edu.utap.catnapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utap.catnapp.R


// XXX Write most of this file
class SelectCats: Fragment() {
    private lateinit var swipe: SwipeRefreshLayout
    private val favoritesFragTag = "favoritesFragTag"
    private val subredditsFragTag = "subredditsFragTag"
    // XXX initialize viewModel
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : CatAdapter
//    private lateinit var adapter: PostRowAdapter

    companion object {
        fun newInstance(): SelectCats {
            return SelectCats()
        }
    }

//    private fun setTitle(newTitle: String) {
//        val title = activity?.findViewById<TextView>(R.id.actionTitle)
//        title?.text = newTitle
//        title?.setOnClickListener {
//            actionSubreddit()
//        }
//    }

//    private fun initTitleObservers() {
//        // Copied from Demo
//        viewModel.observeTitle().observe(viewLifecycleOwner, Observer {
//            setTitle(it.toString())
//        })
//    }
//    private fun initSubredditObservers() {
//    }

//    private fun initFav() {
//        val fav = activity?.findViewById<ImageView>(R.id.actionFavorite)
//        fav?.setOnClickListener {
//            actionFavorite()
//        }
//    }

    // Copied from Demo
//    private fun initSearch() {
//        activity
//            ?.findViewById<EditText>(R.id.actionSearch)
//            ?.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {}
//                override fun beforeTextChanged(
//                    s: CharSequence?, start: Int, count: Int, after: Int) {}
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                    if(s.isEmpty()) (activity as MainActivity).hideKeyboard()
//                    viewModel.setSearchTerm(s.toString())
//                }
//            })
//    }

    // XXX TODO check out addTextChangedListener
//    private fun actionSearch() {
//    }

//    private fun actionSubreddit() {
//        // Copied from Demo
//        parentFragmentManager
//            .beginTransaction()
//            .add(R.id.main_frame, Subreddits.newInstance(), subredditsFragTag)
//            .addToBackStack(null)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            .commit()
//    }

//    private fun actionFavorite() {
//        // Copied from Demo
//        parentFragmentManager
//            .beginTransaction()
//            .add(R.id.main_frame, Favorites.newInstance(), favoritesFragTag)
//            .addToBackStack(null)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            .commit()
//    }

    // Copied from Android Docs (Only Concept)
//    private fun backStack() {
//        parentFragmentManager.addOnBackStackChangedListener {
//            adapter.notifyDataSetChanged()
//            val title = activity?.findViewById<TextView>(R.id.actionTitle)
//            if (title?.text != "Favorites" && title?.text != "Pick") {
//                initFav()
//            }
//        }
//    }

    // Set up the adapter
//    private fun initAdapter(root: View) {
//        // Copied from Demo
//        adapter = PostRowAdapter(viewModel)
//        val recycle = root.findViewById<RecyclerView>(R.id.recyclerView)
//        val manager = LinearLayoutManager(context)
//        recycle.adapter = adapter
//        recycle.layoutManager = manager
//
//        // Copied from Demo
//        viewModel.observeLivePostList().observe(viewLifecycleOwner,
//            Observer {list ->
//                //SSS
//                adapter.submitList(list)
//                adapter.notifyDataSetChanged()
//                //EEE // XXX Observer
//            })
//    }

//    private fun initSwipeLayout(root: View) {
//        // Copied from Demo
//        swipe = root.findViewById(R.id.swipeRefreshLayout)
//        swipe.setOnRefreshListener {
//            if (viewModel.getSearchTerm().isNullOrEmpty()) {
//                viewModel.netPosts()
//            } else {
//                swipe.isRefreshing = false
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_cats, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = gridLayoutManager

        adapter = CatAdapter(viewModel)
        recyclerView.adapter = adapter

        viewModel.observeCats().observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })


//        initSwipeLayout(view)
//        initAdapter(view)
//        initTitleObservers()
//        initFav()
//        initSearch()
        viewModel.netCats()

        // Copied from Demo
//        viewModel.observePosts().observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
//            requireActivity().swipeRefreshLayout.isRefreshing = false
//        })

//        viewModel.setTitleToSubreddit()
//        backStack()

        return view
    }
}