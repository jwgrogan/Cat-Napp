package edu.utap.catnapp.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import edu.utap.catnapp.R
import edu.utap.catnapp.api.Breed
import edu.utap.catnapp.api.CatApi
import edu.utap.catnapp.api.CatPost
import edu.utap.catnapp.api.CatRepository
import edu.utap.catnapp.firebase.CatPhoto
//import edu.utap.catnapp.firebase.FirestoreAuthLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {

        // for firestore
        const val pictureURLKey = "pictureUUIDKey"
        var currentUser: FirebaseUser? = null
        var photos = MutableLiveData<List<CatPhoto>>()

        // clear photos on sign out
        fun clearPhotos() {
            photos.value = listOf()

        }

        // for category selection
        var categories = ""
        var categoryName = ""

        // setup for cat details activity
        const val titleKey = "titleKey"
        const val imageURLKey = "imageURLKey"
        const val descKey = "descKey"
        const val wikiURLKey = "wikiURLKey"
        var breedFlag = false

        fun detailsCatPost(context: Context, catPost: CatPost) {
            val intent = Intent(context, CatDetails::class.java)
            val data = Bundle()
            if (catPost.breeds != emptyList<Breed>()) {
                data.putString(titleKey, catPost.breeds[0].name)
                data.putString(descKey, catPost.breeds[0].description)
                data.putString(wikiURLKey, catPost.breeds[0].wikipedia_url)
                breedFlag = true
            } else {
                breedFlag = false
            }

            data.putString(imageURLKey, catPost.url)

            intent.putExtras(data)
            startActivity(context, intent, data)
        }

        fun detailsCatPhoto(context: Context, catPhoto: CatPhoto) {
            val intent = Intent(context, CatDetails::class.java)
            val data = Bundle()
            if (catPhoto.breed != null) {
                data.putString(titleKey, catPhoto.breed)
                data.putString(descKey, catPhoto.description)
//                data.putString(wikiURLKey, catPhoto.breeds[0].wikipedia_url)
                breedFlag = true
            } else {
                breedFlag = false
            }
            data.putString(imageURLKey, catPhoto.pictureURL)

            intent.putExtras(data)
            startActivity(context, intent, data)
        }
    }

    private val catApi = CatApi.create()
    private val repository = CatRepository(catApi)
    private val cats = MutableLiveData<List<CatPost>>()
    private var favCats = MutableLiveData<List<CatPost>>().apply {
        value = mutableListOf()
    }
    private var selectedCat = MutableLiveData<CatPost>()

    // firebase vars
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
//    private var firebaseAuthLiveData = FirestoreAuthLiveData()
//    private var photos = MutableLiveData<List<CatPhoto>>()
//    private var authListener : ListenerRegistration? = null

    init {
        setCategories(categories)
    }

    fun setCategories(category: String) {
        categories = category
    }

    // cat view refresh
    fun netCats() {
        viewModelScope.launch(
                context = viewModelScope.coroutineContext + Dispatchers.IO) {
            cats.postValue(repository.getNineCats(categories))
        }

    }

    fun observeCats(): LiveData<List<CatPost>> {
        return cats
    }

    // favorites functions
    fun observeFav (): LiveData<List<CatPost>> {
        return favCats
    }

    fun observeLiveFav (): LiveData<List<CatPost>> {
        return favCats
    }

    fun addFav(posts : CatPost) {
        val localList = favCats.value?.toMutableList()
        localList?.let {
            it.add(posts)
            favCats.value = it
        }
    }
    fun isFav(cat: CatPost): Boolean {
        return favCats.value?.contains(cat) ?: false
    }
    fun removeFav(posts: CatPost) {
        val localList = favCats.value?.toMutableList()
        localList?.let {
            it.remove(posts)
            favCats.value = it
        }
    }


    // selection functions
    fun selectCat(cat : CatPost) {
        selectedCat.value = cat
    }

    fun isSelected(cat: CatPost): Boolean {
        return selectedCat.value == cat ?: false
    }

    // firebase auth functions
//    fun observeFirebaseAuthLiveData(): LiveData<FirebaseUser?> {
//        return firebaseAuthLiveData
//    }
//
//    fun getUserId(): String? {
//        return firebaseAuthLiveData.value?.uid
//    }

//    fun signOut() {
//        authListener?.remove()
//        FirebaseAuth.getInstance().signOut()
//        photos.value = listOf()
//    }


    // TODO: signout?
//    fun signOut() {
//        chatListener?.remove()
//        FirebaseAuth.getInstance().signOut()
//        chat.value = listOf()
//    }

    // firestore functions

    fun observePhotos(): LiveData<List<CatPhoto>> {
        return photos
    }

    fun savePhoto(catPhoto: CatPhoto) {
        Log.d(
            "HomeViewModel",
            String.format(
                "saveChatRow ownerUid(%s) name(%s) %s",
                catPhoto.userId,
                catPhoto.username,
                catPhoto.description
            )
        )
        // XXX Write me.
        // https://firebase.google.com/docs/firestore/manage-data/add-data#add_a_document
        // Remember to set the rowID of the chatRow before saving it

        db.collection("globalCats")
            .add(catPhoto)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                db.collection("globalCats").document(documentReference.id)
                    .update("rowId", documentReference.id)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun deletePhoto(catPhoto: CatPhoto){
        // Delete picture (if any) on the server, asynchronously
//        val url = catPhoto.pictureURL
        // TODO: remove if check
//        if (url != null) {
//            Storage.deleteImage(url)
//        }
        Log.d(javaClass.simpleName, "remote chatRow id: ${catPhoto.rowId}")

        // XXX delete chatRow
        db.collection("globalCats").document(catPhoto.rowId).delete()
            .addOnSuccessListener {
                Log.d(
                    javaClass.simpleName,
                    "Chat delete \"${catPhoto.description}\" id: ${catPhoto.rowId}"
                )
                getPhotos()
            }
            .addOnFailureListener { e ->
                Log.d(javaClass.simpleName, "Chat deleting FAILED \"${catPhoto.description}\"")
                Log.w(javaClass.simpleName, "Error adding document", e)
            }
    }

    fun getPhotos() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            photos.value = listOf()
            return
        } else {
//            val photoRef = db.collection("globalCats").orderBy("timeStamp") // return all cats
            val photoRef = db.collection("globalCats")
            photoRef.addSnapshotListener { querySnapshot, ex ->
                if (ex != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    photos.value = querySnapshot.documents.mapNotNull {
                        it.toObject(CatPhoto::class.java)
                    }
                }
            }
        }
    }
}