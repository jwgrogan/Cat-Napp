package edu.utap.catnapp.api

class CatRepository(private val api: CatApi) {
    suspend fun getNineCats(category_id : String) = api.getNineCats(category_id)
}