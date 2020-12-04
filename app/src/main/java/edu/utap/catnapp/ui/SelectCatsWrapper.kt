package edu.utap.catnapp.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.utap.catnapp.MainActivity
import edu.utap.catnapp.R

class SelectCatsWrapper : AppCompatActivity() {
    private lateinit var selectCats: SelectCats
    private var category: String? = null
    private val RC_SIGN_IN = 123

//    fun hideKeyboard() {
//        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0);
//    }

    // https://stackoverflow.com/questions/24838155/set-onclick-listener-on-action-bar-title-in-android/29823008#29823008


    private fun initSelectCats() {
        supportFragmentManager
            .beginTransaction()
            // No back stack for home
            .add(R.id.main_frame, selectCats)
            // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun initFavorites() {
        val initFavorites = findViewById<ImageView>(R.id.actionFavorite)
        initFavorites?.setOnClickListener{
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, FavCats.newInstance())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
        }
    }

    private fun initActionBar(actionBar: ActionBar) {
        // Disable the default and enable the custom
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        val customView: View =
                layoutInflater.inflate(R.layout.action_bar, null)
        // Apply the custom view
        actionBar.customView = customView
    }

    private fun initToolbarUser() {
        val user = Firebase.auth.currentUser
        val toolbarUsername = findViewById<TextView>(R.id.toolbarUsername)
        if (user != null) {
            toolbarUsername?.text = "Hi, " + user.displayName.toString()
        } else {
            toolbarUsername?.text = "Please sign in"
        }
    }

    private fun initToolbarMenu() {
        // setup toolbar menu
        val toolbarMenu = findViewById<TextView>(R.id.actionMenu)
        toolbarMenu.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, toolbarMenu)
            popupMenu.menuInflater.inflate(R.menu.menu_sign_in, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.actionSignIn ->
                        signIn()
                    R.id.actionSignOut ->
                        signOut()
                }
                true
            })
            popupMenu.show()
        }
    }

    private fun signIn() {
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
        )
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        )
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        MainViewModel.clearPhotos()
        val toolbarUsername = findViewById<TextView>(R.id.toolbarUsername)
        toolbarUsername?.text = "Please sign in"
    }

    // check if user has changed
    override fun onResume() {
        super.onResume()
        initToolbarUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_cats_wrapper)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            initActionBar(it)
        }

        initToolbarUser()
        initToolbarMenu()
        initFavorites()

//        val categoryTV = findViewById<TextView>(R.id.categoryTV)
//        if (categoryTV != null) {
//            categoryTV.text = MainViewModel.categoryName
//        }

        category = intent.extras?.getString(MainActivity.categoryKey)
        MainViewModel.categories = category.toString()

        selectCats = SelectCats.newInstance()
        initSelectCats()
    }
}