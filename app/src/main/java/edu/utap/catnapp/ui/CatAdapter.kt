package edu.utap.catnapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.utap.catnapp.R

//class CatAdapter(private val viewModel: MainViewModel)
//    : RecyclerView.Adapter<CatAdapter.VH>() {
//
//    // ViewHolder pattern minimizes calls to findViewById
//    inner class VH(itemView: View)
//        : RecyclerView.ViewHolder(itemView) {
//        // XXX Write me.
//        // NB: This one-liner will exit the current fragment
//        // (itemView.context as FragmentActivity).supportFragmentManager.popBackStack()
//        val subRowPic = itemView.findViewById<ImageView>(R.id.subRowPic)
//        val subRowHeading = itemView.findViewById<TextView>(R.id.subRowHeading)
//        val subRowDetails = itemView.findViewById<TextView>(R.id.subRowDetails)
//
//        init {
//        }
//
//
//
//        fun bind(item: RedditPost){
//            if (item.iconURL != null) {
//                Glide.glideFetch(item.iconURL, item.iconURL, subRowPic)
//            }
//            subRowHeading.text = item.displayName
//            subRowDetails.text = item.publicDescription
//            subRowHeading.setOnClickListener{
////                viewModel.setTitle(item.displayName.toString())
//                viewModel.setSubreddit(item.displayName.toString())
//                viewModel.setTitleToSubreddit()
//                viewModel.netPosts()
//                (itemView.context as FragmentActivity).supportFragmentManager.popBackStack()
//
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_subreddit, parent, false)
//        return VH(itemView)
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.bind(viewModel.observeLiveSubreddits().value!![position])
//    }
//
//    override fun getItemCount(): Int {
//        return viewModel.observeLiveSubreddits().value?.count() ?:0
//    }
//
////    fun addAll(redditPost : List<RedditPost>) {
////        subreddits.addAll(redditPost)
////    }
//}