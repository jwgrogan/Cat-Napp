package edu.utap.catnapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import edu.utap.catnapp.R
import edu.utap.catnapp.api.CatPost
import edu.utap.catnapp.firebase.CatPhoto

class CatAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<CatAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)

        init {
            // TODO: this?
        }


        private fun initSave(item: CatPost) {
            val catPhoto = CatPhoto().apply {
                val user = FirebaseAuth.getInstance().currentUser
                if(user == null) {
                    Toast.makeText(itemView.context, "Please sign in to save this cat!", Toast.LENGTH_SHORT).show()
                    fav.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                } else {
                    username = user.displayName
                    userId = user.uid
                }
                pictureURL = item.url
            }
            viewModel.savePhoto(catPhoto)
            viewModel.getPhotos(FirebaseAuth.getInstance().currentUser?.uid.toString())
        }

        fun bind(item: CatPost){
            // load photos from storage
            viewModel.getPhotos(FirebaseAuth.getInstance().currentUser?.uid.toString())
//            var photos = viewModel.observePhotos().value

//            val userId = FirebaseAuth.getInstance().currentUser?.uid

            // bind images
            val imageURL = item.url
            val glideOptions = RequestOptions().transform(RoundedCorners(20))
            Glide.with(itemView).load(imageURL).apply(glideOptions).override(480, 320).into(gridPic)

            // set up details click
            itemView.setOnClickListener{
                MainViewModel.favFlag = viewModel.isFav(item)
                MainViewModel.detailsCatPost(itemView.context, item)
            }

            // handle adding and removing favs
            fav.setOnClickListener{
                if (viewModel.isFav(item)) { // if fav, clicking removes favorite and deletes from firestore
                    fav.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                    if (viewModel.observePhotos().value != null) {
                        for (photo in viewModel.observePhotos().value!!) {
                            if (item.url == photo.pictureURL) {
                                viewModel.deletePhoto(photo)
                            }

                        }
                    }
                    viewModel.removeFav(item)
                }
                else { // else add to firestore
                    fav.setImageResource(R.drawable.ic_favorite_red_24dp)
                    viewModel.addFav(item)
                    initSave(item)
                }
            }

            // set fav heart
            if (viewModel.observePhotos().value != null) {
                for (photo in viewModel.observePhotos().value!!) {
                    if (item.url == photo.pictureURL) {
                        fav.setImageResource(R.drawable.ic_favorite_red_24dp)
                        viewModel.addFav(item)
                    }
//                    else {
//                        fav.setImageResource(R.drawable.ic_favorite_border_red_24dp)
////                        viewModel.removeFav(item)
//                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_image, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.observeCats().value!![position])
    }

    override fun getItemCount(): Int {
        return viewModel.observeCats().value?.count() ?:0
    }
}