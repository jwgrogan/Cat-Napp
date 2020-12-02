package edu.utap.catnapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import edu.utap.catnapp.R
import edu.utap.catnapp.firebase.FirestoreAdapter
import kotlinx.android.synthetic.main.fragment_select_cats.*

class FavCats : Fragment() {
    // XXX initialize viewModel
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : FirestoreAdapter
//    private var currentUser: FirebaseUser? = null
    private var fragmentUUID: String? = null

    companion object {
//        var currentUser: FirebaseUser? = null
        fun newInstance(): FavCats {
            return FavCats()
        }
    }

//    private fun cancelClick() {
//        val actionSubreddit = activity?.findViewById<TextView>(R.id.actionTitle)
//        val actionFavorite = activity?.findViewById<ImageView>(R.id.actionFavorite)
//        actionSubreddit?.setOnClickListener(null)
//        actionFavorite?.setOnClickListener(null)
//    }

    // TODO: need this if get the main screen favs working
    private fun initAuth() {
        viewModel.observeFirebaseAuthLiveData().observe(viewLifecycleOwner, Observer {
            MainViewModel.currentUser = it
        })
    }

    private fun initAdapter(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.favRecyclerView)
        val gridLayoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = gridLayoutManager

        adapter = FirestoreAdapter(viewModel)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPhotos()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbarTitle)
        val newTitle = "CatNapp/Favorites"
        toolbarTitle?.text = newTitle
//        cancelClick()

//        initAuth()

        val view = inflater.inflate(R.layout.fragment_past_cats, container, false)
        initAdapter(view)

        viewModel.observePhotos().observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
            adapter.notifyDataSetChanged()
//            requireActivity().swipeRefreshLayout.isRefreshing = false
//            requireActivity().swipeRefreshLayout.isEnabled = false
        })

        return view
    }
}