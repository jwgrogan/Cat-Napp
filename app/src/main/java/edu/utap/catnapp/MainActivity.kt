package edu.utap.catnapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import edu.utap.catnapp.ui.FavCats
import edu.utap.catnapp.ui.MainViewModel
import edu.utap.catnapp.ui.SelectCatsWrapper


class MainActivity : AppCompatActivity() {

    companion object {
        const val categoryKey = "categoryKey"
    }

    private var categoryMap = mapOf<String, String>("Hats" to "1", "Space" to "2", "Funny" to "3", "Sunglasses" to "4", "Boxes" to "5", "Caturday" to "6", "Ties" to "7", "Dream" to "9", "Sinks" to "14", "Clothes" to "15")
    private val RC_SIGN_IN = 123

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

    private fun initSelectCats() {
        val content = findViewById<View>(R.id.content_main)
        val spinner = content.findViewById<Spinner>(R.id.categorySpinner)

        if (spinner.selectedItemPosition == 0) {
            val toast = Toast.makeText(this, "Choose a cat-egory!", Toast.LENGTH_LONG)
            toast.show()
        } else {
            // save category name for title
            MainViewModel.categoryName = spinner.getItemAtPosition(spinner.selectedItemPosition).toString()

            val getSelectCatsIntent = Intent(this, SelectCatsWrapper::class.java)
            val result = 1
            val myExtras = Bundle()
            myExtras.putString(categoryKey, categoryMap[spinner.getItemAtPosition(spinner.selectedItemPosition).toString()])
            getSelectCatsIntent.putExtras(myExtras)
            startActivityForResult(getSelectCatsIntent, result)
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
        setContentView(R.layout.activity_main)

        // sign in user with firebase if no user
        if (Firebase.auth.currentUser == null) {
            signIn()
        }


        // set up toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let{
            initActionBar(it)
        }

        initToolbarUser()
        initToolbarMenu()
        initFavorites()

        // set up spinner
        val content = findViewById<View>(R.id.content_main)
        val spinner = content.findViewById<Spinner>(R.id.categorySpinner)
        val categoryTypeAdapter = ArrayAdapter.createFromResource(this,
            R.array.category_type,
            R.layout.spinner_font)
        categoryTypeAdapter.setDropDownViewResource(R.layout.spinner_font)
        spinner.adapter = categoryTypeAdapter

        val button = content.findViewById<Button>(R.id.friendButton)
        button?.setOnClickListener {
            initSelectCats()
        }
    }

    // TODO: delete this?
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.apply {
        }
    }
}