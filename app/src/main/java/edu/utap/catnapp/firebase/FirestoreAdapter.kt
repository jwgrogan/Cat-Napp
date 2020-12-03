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
import edu.utap.catnapp.R
import edu.utap.catnapp.ui.CatAdapter
import edu.utap.catnapp.ui.MainViewModel

class FirestoreAdapter(private val viewModel: MainViewModel)
    : RecyclerView.Adapter<FirestoreAdapter.VH>() {
    // This class allows the adapter to compute what has changed
//    class Diff : DiffUtil.ItemCallback<ChatRow>() {
//        override fun areItemsTheSame(oldItem: ChatRow, newItem: ChatRow): Boolean {
//            return oldItem.rowID == newItem.rowID
//        }
//
//        override fun areContentsTheSame(oldItem: ChatRow, newItem: ChatRow): Boolean {
//            return oldItem.name == newItem.name
//                    && oldItem.ownerUid == newItem.ownerUid
//                    && oldItem.message == newItem.message
//                    && oldItem.pictureUUID == newItem.pictureUUID
//                    && oldItem.timeStamp == newItem.timeStamp
//        }
//    }
//    companion object {
//        private val iphoneTextBlue = Color.parseColor("#1982FC")
//        private val iphoneMessageGreen = ColorDrawable(Color.parseColor("#43CC47"))
//        private val dimGrey = Color.parseColor("#C5C5C5")
//        private val dateFormat =
//            SimpleDateFormat("hh:mm:ss MM-dd-yyyy")
//        private val transparentDrawable = ColorDrawable(Color.TRANSPARENT)
//    }

    // ViewHolder pattern minimizes calls to findViewById
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        // May the Lord have mercy upon my soul
//        private var myUserTV = itemView.findViewById<TextView>(R.id.chatUserTV)
//        private var myTimeTV = itemView.findViewById<TextView>(R.id.chatTimeTV)
//        private var myTextTV = itemView.findViewById<TextView>(R.id.chatTextTV)
//        private var myTextCV = itemView.findViewById<CardView>(R.id.textCV)
//        private var myPicIV = itemView.findViewById<ImageView>(R.id.picIV)
//        private var otherUserTV = itemView.findViewById<TextView>(R.id.otherChatUserTV)
//        private var otherTimeTV = itemView.findViewById<TextView>(R.id.otherChatTimeTV)
//        private var otherTextTV = itemView.findViewById<TextView>(R.id.otherChatTextTV)
//        private var otherTextCV = itemView.findViewById<CardView>(R.id.otherTextCV)
//        private var otherPicIV = itemView.findViewById<ImageView>(R.id.otherPicIV)

        private var gridPic = view.findViewById<ImageView>(R.id.gridImage)
//        private var gridDetails = view.findViewById<TextView>(R.id.gridDetailsTV)
        private var fav = view.findViewById<ImageView>(R.id.gridFav)

        init {
            itemView.isLongClickable = true
        }
//        private fun goneElements(userTV: TextView, timeTV: TextView, textTV: TextView,
//                                 textCV: CardView, picIV: ImageView) {
//            userTV.visibility = View.GONE
//            timeTV.visibility = View.GONE
//            textTV.visibility = View.GONE
//            textCV.visibility = View.GONE
//            picIV.visibility = View.GONE
//        }
//        private fun visibleElements(userTV: TextView, timeTV: TextView, textTV: TextView,
//                                    textCV: CardView, picIV: ImageView) {
//            userTV.visibility = View.VISIBLE
//            timeTV.visibility = View.VISIBLE
//            textTV.visibility = View.VISIBLE
//            textCV.visibility = View.VISIBLE
//            picIV.visibility = View.VISIBLE
//        }
//        private fun bindElements(item: CatPhoto, backgroundColor: Int, textColor: Int,
//                                 userTV: TextView, timeTV: TextView, textTV: TextView,
//                                 textCV: CardView, picIV: ImageView) {
//            // Set background on CV, not TV because...layout is weird
//            textCV.setCardBackgroundColor(backgroundColor)
//            textTV.setTextColor(textColor)
//            userTV.text = item.name
//            textTV.text = item.message
//            itemView.setOnLongClickListener {
//                viewModel.deletePhoto(item)
//                true
//            }
//            // XXX Write me, bind picIV using pictureUUID.
//            if (item.pictureUUID != "") {
//                item.pictureUUID?.let { Glide.with(itemView).load(it).into(gridPic) }
//
////                viewModel.glideFetch(item.pictureUUID as String, picIV)
////                picIV.visibility = View.VISIBLE
//            } else {
////                Toast.makeText(this, "Oh-no! We lost your photo :(", Toast.LENGTH_SHORT).show()
//            }
//
//            if (item.timeStamp == null) {
//                timeTV.text = ""
//            } else {
//                //Log.d(javaClass.simpleName, "date ${item.timeStamp}")
//                timeTV.text = dateFormat.format(item.timeStamp.toDate())
//            }
//        }
        fun bind(item: CatPhoto) {

//            val imageURL = item?.pictureURL
//            Glide.with(itemView).load(imageURL).into(gridPic)

//            if (item == null) return
            if (viewModel.myUid() == item.userId) {
                itemView.setOnLongClickListener {
                    viewModel.deletePhoto(item)
                    true
                }

//                val imageURL = item?.pictureURL
//                Glide.with(itemView).load(imageURL).into(gridPic)

                item.pictureURL?.let { Glide.with(itemView).load(it).into(gridPic) }

                // XXX Write me, bind picIV using pictureUUID.
//                if (item.pictureURL != "") {
//                    item.pictureURL?.let { Glide.with(itemView).load(it).into(gridPic) }
//                } else {
////                Toast.makeText(this, "Oh-no! We lost your photo :(", Toast.LENGTH_SHORT).show()
//                }
            }
            else {
                gridPic.visibility = View.GONE
//                gridDetails.visibility = View.GONE
                fav.visibility = View.GONE        }
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