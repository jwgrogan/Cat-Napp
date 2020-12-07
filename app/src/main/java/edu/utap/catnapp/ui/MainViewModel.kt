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
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var photos = MutableLiveData<List<CatPhoto>>()

        // clear photos on sign out
        fun clearPhotos() {
            photos.value = listOf()

        }

        // for category selection
        var categories = ""
        var categoryName = ""

        // cat details
        const val titleKey = "titleKey"
        const val imageURLKey = "imageURLKey"
        const val descKey = "descKey"
        const val rowIdKey = "rowIdKey"
//        const val wikiURLKey = "wikiURLKey"
        var breedFlag = false
        var favFlag = false
        var commentFlag = false

        fun saveComments (comments: String, rowId: String) {
            db.collection("globalCats").document(rowId).update("description", comments)
        }

        // launch details from select cats
        fun detailsCatPost(context: Context, catPost: CatPost) {
            val intent = Intent(context, CatDetails::class.java)
            val data = Bundle()
            if (catPost.breeds != emptyList<Breed>()) {
                data.putString(titleKey, catPost.breeds[0].name)
                data.putString(descKey, catPost.breeds[0].description)
//                data.putString(wikiURLKey, catPost.breeds[0].wikipedia_url)
                breedFlag = true
            } else {
                breedFlag = false
            }


            commentFlag = false

            data.putString(imageURLKey, catPost.url)

            intent.putExtras(data)
            startActivity(context, intent, data)
        }

        // launch details from favorites
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
            favFlag = true
            commentFlag = true


            data.putString(rowIdKey, catPhoto.rowId)
            data.putString(descKey, catPhoto.description)
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

    // firestore functions
    fun observePhotos(): LiveData<List<CatPhoto>> {
        return photos
    }

    fun savePhoto(catPhoto: CatPhoto) {
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
        db.collection("globalCats").document(catPhoto.rowId).delete()
    }

    fun getPhotos(userId: String) {
        if (FirebaseAuth.getInstance().currentUser == null) {
            photos.value = listOf()
            return
        } else {
            val photoRef = db.collection("globalCats").whereEqualTo("userId", userId)
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