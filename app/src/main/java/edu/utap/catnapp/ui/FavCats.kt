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
import com.google.firebase.auth.FirebaseUser
import edu.utap.catnapp.R
import edu.utap.catnapp.firebase.FirestoreAdapter

class FavCats : Fragment() {
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : FirestoreAdapter
    private var currentUser: FirebaseUser? = null

    companion object {
        fun newInstance(): FavCats {
            return FavCats()
        }
    }

    // TODO: need this if get the main screen favs working
    private fun initAuth() {
        viewModel.observeFirebaseAuthLiveData().observe(viewLifecycleOwner, Observer {
            currentUser = it
            MainViewModel.currentUser = currentUser
        })
    }

    private fun initAdapter(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.favRecyclerView)
        val gridLayoutManager = GridLayoutManager(context, 2)
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
//        val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbarTitle)
//        val newTitle = "CatNapp/Favorites"
//        toolbarTitle?.text = newTitle
//        cancelClick()

//        initAuth()
        // TODO: add favorits label bar

        val view = inflater.inflate(R.layout.fragment_past_cats, container, false)
        initAdapter(view)

//        viewModel.getPhotos()

        viewModel.observePhotos().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        return view
    }
}