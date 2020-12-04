package edu.utap.catnapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import edu.utap.catnapp.R
import kotlinx.android.synthetic.main.cat_details.*

class CatDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_details)

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
            breed = "am I your new faFURite cat??"
            description = "i reallly enjoy lazer pointerz and promise to always be cuddly :)"
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

        if (MainViewModel.favFlag) {
            detailsFav.setImageResource(R.drawable.ic_favorite_red_24dp)
        }


        // set up sharing
        // https://www.tutorialspoint.com/android/android_twitter_integration.htm
//        shareBTN.setOnClickListener {
//            val sharingIntent = Intent(Intent.ACTION_SEND)
//            val imageURI = Uri.parse(imageURL)
//
//            // TODO: check if uri is valid? prob not needed
//            val stream = contentResolver.openInputStream(imageURI)
//
//            sharingIntent.type = "image/jpeg";
//            // TODO: verify if this needs to be uri or url
//            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageURI);
//            startActivity(Intent.createChooser(sharingIntent, "Share"));
//            }

        shareBTN.setOnClickListener {
            val myIntent = Intent()
            myIntent.action = Intent.ACTION_SEND
            myIntent.type = "image/jpeg";
//            val shareSub = "Your subject";
//            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT, imageURL);
            val imageURI = Uri.parse(imageURL)
//            val stream = contentResolver.openInputStream(imageURI)
            myIntent.putExtra(Intent.EXTRA_STREAM, imageURI)
            startActivity(Intent.createChooser(myIntent, "Share using"));
        }
    }
}