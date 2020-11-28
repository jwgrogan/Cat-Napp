package edu.utap.catnapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import edu.utap.catnapp.R
import kotlinx.android.synthetic.main.one_cat.*

class OneCat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_cat)

        setSupportActionBar(oneCatToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        oneCatToolbar.setNavigationOnClickListener{
            finish()
        }

        var breed = ""
        var description = ""
        var wikiURL = ""

        if (MainViewModel.breedFlag) {
            breed = intent.extras?.getString(MainViewModel.titleKey).toString()
            description = intent.extras?.getString(MainViewModel.descKey).toString()
            wikiURL = intent.extras?.getString(MainViewModel.wikiURLKey).toString()
        }
        else {
            // TODO: make randomized list of fun cat facts
            breed = "hmm... i'm a mystery cat!"
            description = "i probably enjoy lazer pointerz"
            wikiURL = ""
        }

        val imageURL = intent.extras?.getString(MainViewModel.imageURLKey)
//        val thumbnailURL = intent.extras?.getString(MainViewModel.thumbnailURLKey)

//        if (title.length > 30){
//            onePostShortTitleTV.text = title.take(30) + "..."
//        } else {
//            onePostShortTitleTV.text = title
//        }




        oneCatBreedTV.text = breed
        oneCatDescriptionTV.text = description
        // TODO: make wiki link clickable
        oneCatWikiTV.text = wikiURL
        Glide.with(this).load(imageURL).into(oneCatIV)

//        if (thumbnailURL.takeLast(4) == ".jpg"){
//            Glide.glideFetch(imageURL, thumbnailURL, onePostIV)
//        }
    }
}