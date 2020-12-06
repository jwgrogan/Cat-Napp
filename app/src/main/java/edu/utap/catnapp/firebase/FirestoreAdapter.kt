package edu.utap.catnapp.firebase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import edu.utap.catnapp.R
import edu.utap.catnapp.ui.MainViewModel

class FirestoreAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<FirestoreAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)

        init {
        }

        fun bind(item: CatPhoto) {

//            val imageURL = item?.pictureURL
//            Glide.with(itemView).load(imageURL).into(gridPic)

//            if (item == null) return
            val userId = FirebaseAuth.getInstance().currentUser?.uid
//            fav.setImageResource(R.drawable.ic_favorite_red_24dp)
//            val userId = Firebase.auth.currentUser?.displayName
//            if (viewModel.getUserId() == item.userId) {
            if (userId == item.userId) {
                itemView.setOnClickListener{
                    MainViewModel.detailsCatPhoto(itemView.context, item)
                }

                val imageURL = item.pictureURL
                val glideOptions = RequestOptions().transform(RoundedCorners(20))
                Glide.with(itemView).load(imageURL).apply(glideOptions).override(480, 320).into(gridPic)

                // TODO: sometimes heart doesn't show in favs
//                fav.setImageResource(R.drawable.ic_favorite_red_24dp)

                // delete from favs if user clicks heart
                fav.setOnClickListener {
                    fav.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                    viewModel.deletePhoto(item)
                }
            }
            else {
                // old solution - no longer needed but keeping as an idea to add ads later?
                // makes it an ad... still need better solution
                val imageURL = "https://mrvirk.com/wp-content/uploads/2018/07/google-ads-logo-2019.png"
                val glideOptions = RequestOptions().transform(RoundedCorners(20))
                Glide.with(itemView).load(imageURL).apply(glideOptions).override(480, 320).into(gridPic)

                fav.visibility = View.GONE

                itemView.setOnClickListener {
                    val url = "https://ads.google.com/intl/en_us/getstarted/?subid=us-en-adon-awa-sch-c-bkc!o3~a082a9071a10129b12b6f6cd8a35f6ca~p42103997045~3p.ds"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    val data = Bundle()
                    data.putString(MainViewModel.imageURLKey, url)
                    startActivity(itemView.context, intent, data)
                }
            }

            fav.setImageResource(R.drawable.ic_favorite_red_24dp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_image, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.observePhotos().value!![position])
    }

    override fun getItemCount(): Int {
        return viewModel.observePhotos().value?.count() ?:0
    }
}