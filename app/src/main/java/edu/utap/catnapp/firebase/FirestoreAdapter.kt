package edu.utap.catnapp.firebase

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.utap.catnapp.R
import edu.utap.catnapp.ui.CatAdapter
import edu.utap.catnapp.ui.MainViewModel

class FirestoreAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<FirestoreAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)

        init {
//            itemView.isLongClickable = true
        }

        fun bind(item: CatPhoto) {

//            val imageURL = item?.pictureURL
//            Glide.with(itemView).load(imageURL).into(gridPic)

//            if (item == null) return
            val userId = FirebaseAuth.getInstance().currentUser?.uid
//            val userId = Firebase.auth.currentUser?.displayName
//            if (viewModel.getUserId() == item.userId) {
            if (userId == item.userId) {

//                itemView.setOnLongClickListener {
//                    viewModel.deletePhoto(item)
//                    true
//                }
                itemView.setOnClickListener{
                    MainViewModel.detailsCatPhoto(itemView.context, item)
                }

                val imageURL = item.pictureURL
                val glideOptions = RequestOptions().transform(RoundedCorners(20))
                Glide.with(itemView).load(imageURL).apply(glideOptions).override(480, 320).into(gridPic)

//                item.pictureURL?.let { Glide.with(itemView).load(it).into(gridPic) }
                fav.setImageResource(R.drawable.ic_favorite_black_24dp)

                // delete from favs if user clicks heart
                fav.setOnClickListener {
                    fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    viewModel.deletePhoto(item)
                }
            }
            else {
                gridPic.visibility = View.GONE
//                gridDetails.visibility = View.GONE
                fav.visibility = View.GONE
//                viewModel.deletePhoto(item)
                // TODO: figure out how to stop bind if it isn't a users fav


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_image, parent, false)
        //Log.d(MainActivity.TAG, "Create VH")
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //Log.d(MainActivity.TAG, "Bind pos $position")
//        holder.bind(getItem(holder.adapterPosition))
        holder.bind(viewModel.observePhotos().value!![position])
    }

    override fun getItemCount(): Int {
        return viewModel.observePhotos().value?.count() ?:0
    }
}