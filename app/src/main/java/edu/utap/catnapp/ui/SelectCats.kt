package edu.utap.catnapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.utap.catnapp.R

class SelectCats: Fragment() {
    private lateinit var swipe: SwipeRefreshLayout
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : CatAdapter
    private var currentUser: FirebaseUser? = null


    companion object {
        fun newInstance(): SelectCats {
            return SelectCats()
        }
    }

    // XXX TODO check out addTextChangedListener
//    private fun actionSearch() {
//    }

    private fun initSwipeLayout(root: View) {
        swipe = root.findViewById(R.id.swipeRefreshLayout)
        swipe.setOnRefreshListener {
                viewModel.netCats()
                swipe.isRefreshing = false
        }
    }

    private fun initAuth() {
        viewModel.observeFirebaseAuthLiveData().observe(viewLifecycleOwner, Observer {
            currentUser = it
            MainViewModel.currentUser = currentUser
        })
    }

//    private fun initFavorites() {
//        val initFavorites = activity?.findViewById<ImageView>(R.id.actionFavorite)
//        initFavorites?.setOnClickListener{
//            parentFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_frame, FavCats.newInstance(), favoritesFragTag)
//                .addToBackStack(null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // set username in toolbar
        val toolbarUsername = activity?.findViewById<TextView>(R.id.toolbarUsername)
        val username = "Hi, " + Firebase.auth.currentUser?.displayName.toString()
        toolbarUsername?.text = username

        // set category title
        val categoryTV = activity?.findViewById<TextView>(R.id.categoryTV)
        if (categoryTV != null) {
            categoryTV.text = MainViewModel.categoryName
        }

        initAuth()
//        initFavorites()


        val view = inflater.inflate(R.layout.fragment_select_cats, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = CatAdapter(viewModel)
        recyclerView.adapter = adapter

        viewModel.netCats()

        viewModel.observeCats().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })


        initSwipeLayout(view)

        return view
    }
}