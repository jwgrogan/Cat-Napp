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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utap.catnapp.R

class FavCats : Fragment() {
    // XXX initialize viewModel
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter : PostRowAdapter

    companion object {
        fun newInstance(): FavCats {
            return FavCats()
        }
    }

    private fun cancelClick() {
        val actionSubreddit = activity?.findViewById<TextView>(R.id.actionTitle)
        val actionFavorite = activity?.findViewById<ImageView>(R.id.actionFavorite)
        actionSubreddit?.setOnClickListener(null)
        actionFavorite?.setOnClickListener(null)
    }

    private fun initAdapter(root: View) {
        adapter = PostRowAdapter(viewModel)
        val recView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = adapter

        viewModel.observeLiveFav().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            requireActivity().swipeRefreshLayout.isRefreshing = false
            requireActivity().swipeRefreshLayout.isEnabled = false
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toolbarTitle = activity?.findViewById<TextView>(R.id.actionTitle)
        toolbarTitle?.text = "Favorites"
        cancelClick()

        val view = inflater.inflate(R.layout.fragment_rv, container,false)
        initAdapter(view)
        viewModel.observeFav().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            requireActivity().swipeRefreshLayout.isRefreshing = false
            requireActivity().swipeRefreshLayout.isEnabled = false
        })

        return view
}