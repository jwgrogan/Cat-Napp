package edu.utap.catnapp.ui

import android.content.Intent
import android.net.Uri
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

        val breed: String
        val description: String
        val wikiURL: String

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

        if (title.length > 30){
            oneCatBreedTV.text = breed.take(30) + "..."
        } else {
            oneCatBreedTV.text = breed
        }

        oneCatDescriptionTV.text = description
        // TODO: make wiki link clickable
        oneCatWikiTV.text = wikiURL
        Glide.with(this).load(imageURL).into(oneCatIV)


        // set up sharing
        // https://www.tutorialspoint.com/android/android_twitter_integration.htm
        shareBTN.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            val imageURI = Uri.parse(imageURL)

            // TODO: check if uri is valid? prob not needed
//            val stream = contentResolver.openInputStream(imageURI)

            sharingIntent.type = "image/jpeg";
            // TODO: verify if this needs to be uri or url
            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageURL);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
    }
}