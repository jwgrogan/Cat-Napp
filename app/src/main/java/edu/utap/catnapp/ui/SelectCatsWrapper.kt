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

    private fun initSelectCats() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, selectCats)
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
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        val customView: View =
                layoutInflater.inflate(R.layout.action_bar, null)
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
        // setup sign in and sign out menu
        val toolbarMenu = findViewById<TextView>(R.id.actionMenu)
        toolbarMenu.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val popupMenu: PopupMenu = PopupMenu(this, toolbarMenu)
                popupMenu.menuInflater.inflate(R.menu.menu_sign_in, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.actionSignIn ->
                            signIn()
                    }
                    true
                })
                popupMenu.show()
            }
            else {
                val popupMenu: PopupMenu = PopupMenu(this, toolbarMenu)
                popupMenu.menuInflater.inflate(R.menu.menu_sign_out, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.actionSignOut ->
                            signOut()
                    }
                    true
                })
                popupMenu.show()
            }
        }
    }

    private fun signIn() {
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
        )
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

        category = intent.extras?.getString(MainActivity.categoryKey)
        MainViewModel.categories = category.toString()

        selectCats = SelectCats.newInstance()
        initSelectCats()
    }
}