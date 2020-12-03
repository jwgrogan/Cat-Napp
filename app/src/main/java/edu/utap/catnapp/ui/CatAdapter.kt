package edu.utap.catnapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import edu.utap.catnapp.R
import edu.utap.catnapp.api.CatPost
import edu.utap.catnapp.firebase.CatPhoto
import edu.utap.catnapp.firebase.Storage
import java.io.File

class CatAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<CatAdapter.VH>() {

    // ViewHolder pattern minimizes calls to findViewById
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)

        init {
            // TODO: this?
        }


        private fun initSave(item: CatPost) {
            val catPhoto = CatPhoto().apply {
                val cUser = MainViewModel.currentUser
                if(cUser == null) {
                    username = "unknown"
                    userId = "unknown"
                    Log.d("HomeFragment", "XXX, currentUser null!")
                } else {
                    username = cUser.displayName
                    userId = cUser.uid
                }

                pictureURL = item.url
            }
            viewModel.savePhoto(catPhoto)
            // TODO: maybe implement photo upload to storage
//            val localPhotoFile = File(storageDir, "${pictureURL}.jpg")
//            Storage.uploadImage(localPhotoFile, pictureUUID) {
//                Log.d(javaClass.simpleName, "uploadImage callback ${pictureUUID}")
//                photoSuccess(pictureUUID)
//                photoSuccess = ::defaultPhoto
////            state.get<(String)->Unit>(photoSuccessKey)?.invoke(pictureUUID)
////            state.set(photoSuccessKey, ::defaultPhoto)
//                state.set(pictureUUIDKey, "")
        }

        fun bind(item: CatPost){
            viewModel.getPhotos()
            var photos = viewModel.observePhotos().value

            val imageURL = item.url
            val glideOptions = RequestOptions().transform(RoundedCorners(20))
            Glide.with(itemView).load(imageURL).apply(glideOptions).override(480, 320).into(gridPic)

            itemView.setOnClickListener{
                MainViewModel.detailsCatPost(itemView.context, item)
            }

            // set fav heart
            // TODO: this only works in the same session - fix
//            if (photos.contains(item)) {
//
//            }

            if (photos != null) {
                for (photo in photos) {
                    if (item.url == photo.pictureURL) {
                        fav.setImageResource(R.drawable.ic_favorite_black_24dp)
                    } else {
                        fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    }

                }
            }
//            if (viewModel.isFav(item)) {
//                fav.setImageResource(R.drawable.ic_favorite_black_24dp)
//            } else {
//                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
//            }

            // TODO: move this into function above
            fav.setOnClickListener{
                if (viewModel.isFav(item)) { // if fave, clicking removes favorite and deletes from firestore
                    fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    viewModel.removeFav(item)
                    if (photos != null) {
                        for (photo in photos) {
                            if (item.url == photo.pictureURL) {
                                viewModel.deletePhoto(photo)
                            }

                        }
                    }
                }
                else { // else add to firestore
                    fav.setImageResource(R.drawable.ic_favorite_black_24dp)
                    viewModel.addFav(item)
                    initSave(item)
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