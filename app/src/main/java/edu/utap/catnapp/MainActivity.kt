package edu.utap.catnapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import edu.utap.catnapp.ui.SelectCatsWrapper

class MainActivity : AppCompatActivity() {
    // This allows us to do better testing
    companion object {
        var globalDebug = false
        const val categoryKey = "categoryKey"
    }
    private var categoryMap = mapOf<String, String>("Hats" to "1", "Space" to "2", "Funny" to "3", "Sunglasses" to "4", "Boxes" to "5", "Caturday" to "6", "Ties" to "7", "Dream" to "9", "Sinks" to "14", "Clothes" to "15")

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0);
    }

    // https://stackoverflow.com/questions/24838155/set-onclick-listener-on-action-bar-title-in-android/29823008#29823008
    private fun initActionBar(actionBar: ActionBar) {
        // Disable the default and enable the custom
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        val customView: View =
            layoutInflater.inflate(R.layout.action_bar, null)
        // Apply the custom view
        actionBar.customView = customView
    }

//    private fun initDebug() {
//        if(globalDebug) {
//            assets.list("")?.forEach {
//                Log.d(javaClass.simpleName, "Asset file: $it" )
//            }
//            jsonAww100 = assets.open("aww.hot.1.100.json.transformed.txt").bufferedReader().use {
//                it.readText()
//            }
//            subreddit1 = assets.open("subreddits.1.json.txt").bufferedReader().use {
//                it.readText()
//            }
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            initActionBar(it)
        }

        val content = findViewById<View>(R.id.content_main)
        val spinner = content.findViewById<Spinner>(R.id.categorySpinner)
        val categoryTypeAdapter = ArrayAdapter.createFromResource(this,
            R.array.category_type,
            android.R.layout.simple_spinner_item)
        categoryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = categoryTypeAdapter

        // set up buttons for each category, init fragment for each
//        initDebug()
        val button = content.findViewById<Button>(R.id.friendButton)
        button?.setOnClickListener {
            initSelectCats()
        }
    }

    private fun initSelectCats() {
        //  XXX Write me.  Toast on empty name, otherwise launch GuessingGame
        val content = findViewById<View>(R.id.content_main)
        val spinner = content.findViewById<Spinner>(R.id.categorySpinner)

        if (spinner.selectedItemPosition == 0) {
            val toast = Toast.makeText(this, "Choose a cat-egory!", Toast.LENGTH_LONG)
            toast.show()
        } else {
            val getSelectCatsIntent = Intent(this, SelectCatsWrapper::class.java)
            val result = 1
            val myExtras = Bundle()
            myExtras.putString(categoryKey, categoryMap[spinner.getItemAtPosition(spinner.selectedItemPosition).toString()])
            getSelectCatsIntent.putExtras(myExtras)
            startActivityForResult(getSelectCatsIntent, result)
        }
    }

    // NB: If you declare data: Intent, you get onActivityResult overrides nothing
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // XXX Write me
        data?.extras?.apply {
            // Not sure if this is needed at all
            // Maybe pass favourites back?
            // If we want the favourites heart in toolbar
            // On this page
        }
    }
}