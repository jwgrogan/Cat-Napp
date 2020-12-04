package edu.utap.catnapp.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

// TODO: can prob delete this calss if not used
//class FirestoreAuthLiveData : LiveData<FirebaseUser?>() {
//    private val firebaseAuth = FirebaseAuth.getInstance()
//    private val authStateListener = FirebaseAuth.AuthStateListener {
//        value = firebaseAuth.currentUser
//    }
//
//    override fun onActive() {
//        super.onActive()
//        firebaseAuth.addAuthStateListener(authStateListener)
//    }
//
//    override fun onInactive() {
//        super.onInactive()
//        firebaseAuth.removeAuthStateListener(authStateListener)
//    }
//}