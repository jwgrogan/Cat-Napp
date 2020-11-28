package edu.utap.catnapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.utap.catnapp.R
import edu.utap.catnapp.api.CatPost

class CatAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<CatAdapter.VH>() {

    // ViewHolder pattern minimizes calls to findViewById
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        // XXX Write me.
        // NB: This one-liner will exit the current fragment
        // (itemView.context as FragmentActivity).supportFragmentManager.popBackStack()
        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
        private var gridText = view.findViewById<TextView>(R.id.gridText)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)
//        val subRowDetails = itemView.findViewById<TextView>(R.id.subRowDetails)

        init {
            // TODO: this?
        }



        fun bind(item: CatPost){
//            if (item.url != null) {
//                Glide.glideFetch(item.iconURL, item.iconURL, subRowPic)
//            }

            val imageURL = item.url
            Glide.with(itemView).load(imageURL).into(gridPic)

            itemView.setOnClickListener{
                MainViewModel.doOneCat(itemView.context, item)
            }

            if (viewModel.isFav(item)) {
                fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }

            fav.setOnClickListener{
                if (viewModel.isFav(item)) {
                    fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    viewModel.removeFav(item)
                } else {
                    fav.setImageResource(R.drawable.ic_favorite_black_24dp)
                    viewModel.addFav(item)
                }
            }

//            subRowHeading.text = item.displayName
//            subRowDetails.text = item.publicDescription
//            subRowHeading.setOnClickListener{
////                viewModel.setTitle(item.displayName.toString())
//                viewModel.setSubreddit(item.displayName.toString())
//                viewModel.setTitleToSubreddit()
//                viewModel.netPosts()
//                (itemView.context as FragmentActivity).supportFragmentManager.popBackStack()

//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_image, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.bind(viewModel.observeCats().value!![position])
    }

    override fun getItemCount(): Int {
        return viewModel.observeCats().value?.count() ?:0
    }

//    fun addAll(redditPost : List<RedditPost>) {
//        subreddits.addAll(redditPost)
//    }
}