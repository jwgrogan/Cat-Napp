package edu.utap.catnapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.utap.catnapp.R

class PastCats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_past_cats)
    }
}