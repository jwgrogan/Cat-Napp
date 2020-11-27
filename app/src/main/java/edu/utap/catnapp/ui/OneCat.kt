package edu.utap.catnapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.utap.catnapp.R

class OneCat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_cat)

//        setSupportActionBar(onePostToolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        onePostToolbar.setNavigationOnClickListener{
//            finish()
//        }

//        val title = intent.extras.getString(MainViewModel.titleKey)
//        val imageURL = intent.extras.getString(MainViewModel.imageURLKey)
//        val thumbnailURL = intent.extras.getString(MainViewModel.thumbnailURLKey)

//        if (title.length > 30){
//            onePostShortTitleTV.text = title.take(30) + "..."
//        } else {
//            onePostShortTitleTV.text = title
//        }
//        onePostLongTitleTV.text = title

//        if (thumbnailURL.takeLast(4) == ".jpg"){
//            Glide.glideFetch(imageURL, thumbnailURL, onePostIV)
//        }
    }
}