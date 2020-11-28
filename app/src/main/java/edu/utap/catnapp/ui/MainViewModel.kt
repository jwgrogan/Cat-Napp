package edu.utap.catnapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.catnapp.api.CatApi
import edu.utap.catnapp.api.CatPost
import edu.utap.catnapp.api.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private var categories = ""
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


    // launch single cat image view
    companion object {
        const val titleKey = "titleKey"
        const val imageURLKey = "imageURLKey"
//        const val thumbnailURLKey = "thumbnailURLKey"
//        const val textKey = "textKey"
        fun doOneCat(context: Context, catPost: CatPost) {
            val intent = Intent(context, OneCat::class.java)
            val data = Bundle()
            // TODO: link to xml
//            data.putString(titleKey, redditPost.title.toString())
//            data.putString(textKey, redditPost.selfText.toString())
//            data.putString(imageURLKey, redditPost.imageURL)
//            data.putString(thumbnailURLKey, redditPost.thumbnailURL)
//            intent.putExtras(data)

            ContextCompat.startActivity(context, intent, data)
        }
    }
}