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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.utap.catnapp.MainActivity
import edu.utap.catnapp.R
import edu.utap.catnapp.firebase.FirestoreAdapter

class FavCats : Fragment() {
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : FirestoreAdapter

    companion object {
        fun newInstance(): FavCats {
            return FavCats()
        }
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
        viewModel.getPhotos(FirebaseAuth.getInstance().currentUser?.uid.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fav_cats, container, false)
        initAdapter(view)

        viewModel.observePhotos().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        return view
    }
}