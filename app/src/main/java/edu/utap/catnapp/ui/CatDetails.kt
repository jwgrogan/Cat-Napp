package edu.utap.catnapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import edu.utap.catnapp.MainActivity
import edu.utap.catnapp.R
import kotlinx.android.synthetic.main.cat_details.*

class CatDetails : AppCompatActivity() {

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_details)

        setSupportActionBar(oneCatToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        oneCatToolbar.setNavigationOnClickListener{
            finish()
        }

//        var photos = MainViewModel.photos.value
//        val breed: String
//        val description: String
//        val wikiURL: String

//        if (MainViewModel.breedFlag) {
//            breed = intent.extras?.getString(MainViewModel.titleKey).toString()
//            description = intent.extras?.getString(MainViewModel.descKey).toString()
//            wikiURL = intent.extras?.getString(MainViewModel.wikiURLKey).toString()
//        }
//        else {
//            // TODO: make randomized list of fun cat facts
//            breed = "am I your new faFURite cat??"
//            description = "i reallly enjoy lazer pointerz and promise to always be cuddly :)"
//            wikiURL = ""
//        }

        val title = "Oh hey there..."
        val imageURL = intent.extras?.getString(MainViewModel.imageURLKey)
        val rowId = intent.extras?.getString(MainViewModel.rowIdKey).toString()
        val comments = intent.extras?.getString(MainViewModel.descKey).toString()

//        if (title.length > 30){
//            oneCatBreedTV.text = breed.take(30) + "..."
//        } else {
//            oneCatBreedTV.text = breed
//        }

        // set up comments if cat is favorite
        if (MainViewModel.commentFlag) {
//            editCommentsET.isEnabled = true
//            saveCommentsBTN.isEnabled = true
            saveCommentsBTN.setOnClickListener {
                if (editCommentsET.text.isNotEmpty()) {
                    MainViewModel.saveComments(editCommentsET.text.toString(), rowId)
                    commentsTV.text = editCommentsET.text.toString()
                    editCommentsET.text.clear()
                    Toast.makeText(this, "Comments saved!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter comments", Toast.LENGTH_SHORT).show()
                }
            }
            if (comments != "null") {
                commentsTV.text = comments
            } else {
                commentsTV.text = "Enter comments and see them here!"
            }
        }
        else {
//            editCommentsET.isEnabled = false
//            saveCommentsBTN.isEnabled = false
            saveCommentsBTN.setOnClickListener {
                editCommentsET.text.clear()
                Toast.makeText(this, "Save this cat to see and enter comments!", Toast.LENGTH_LONG).show()
            }
            if (comments != "null") {
                commentsTV.text = comments
            } else {
                commentsTV.text = "Save this cat to see and enter comments!"
            }
        }

        // handle if user hits enter in edit text
        editCommentsET.setOnEditorActionListener { /*v*/_, actionId, event ->
            if ((event != null
                            &&(event.action == KeyEvent.ACTION_DOWN)
                            &&(event.keyCode == KeyEvent.KEYCODE_ENTER))
                    || (actionId == EditorInfo.IME_ACTION_DONE)) {
                hideKeyboard()
                saveCommentsBTN.callOnClick()
            }
            true
        }

        oneCatBreedTV.text = title
        Glide.with(this).load(imageURL).into(catDetailsIV)



//        oneCatWikiTV.text = wikiURL



        // set fav indicator
        if (MainViewModel.favFlag) {
            detailsFav.setImageResource(R.drawable.ic_favorite_red_24dp)
        }

        // handle lack of full features for favs
        detailsFav.setOnClickListener {
            Toast.makeText(this, "Sorry! we are still in Beta - please go back to save/remove this cat :)", Toast.LENGTH_LONG).show()
        }

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