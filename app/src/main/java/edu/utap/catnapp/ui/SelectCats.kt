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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.utap.catnapp.R

class SelectCats: Fragment() {
    private lateinit var swipe: SwipeRefreshLayout
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : CatAdapter

    companion object {
        fun newInstance(): SelectCats {
            return SelectCats()
        }
    }

    private fun initSwipeLayout(root: View) {
        swipe = root.findViewById(R.id.swipeRefreshLayout)
        swipe.setOnRefreshListener {
                viewModel.netCats()
                swipe.isRefreshing = false
        }
    }

    private fun initAdapter(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter = CatAdapter(viewModel)
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

        val view = inflater.inflate(R.layout.fragment_select_cats, container, false)
        initAdapter(view)
        initSwipeLayout(view)

        viewModel.netCats()

        viewModel.observeCats().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        return view
    }
}