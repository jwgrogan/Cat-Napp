package edu.utap.catnapp.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import edu.utap.catnapp.MainActivity
import edu.utap.catnapp.R

class SelectCatsWrapper : AppCompatActivity() {
    // This allows us to do better testing
    companion object {
        var globalDebug = false
        lateinit var jsonAww100: String
        lateinit var subreddit1: String
    }
    private lateinit var selectCats: SelectCats
    private var category: String? = null

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

    private fun initSelectCats() {
        supportFragmentManager
            .beginTransaction()
            // No back stack for home
            .add(R.id.main_frame, selectCats)
            // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
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
        setContentView(R.layout.select_cats_wrapper)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            initActionBar(it)
        }

        category = intent.extras?.getString(MainActivity.categoryKey)
        MainViewModel.categories = category.toString()

        // set up buttons for each category, init fragment for each
        selectCats = SelectCats.newInstance()
        initSelectCats()
//        initDebug()
    }
}