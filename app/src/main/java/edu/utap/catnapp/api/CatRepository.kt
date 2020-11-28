package edu.utap.catnapp.api

class CatRepository(private val api: CatApi) {
    suspend fun getNineCats(category_id : String, key: String) = api.getNineCats(category_id, "ebb49bcd-81c8-450f-9149-64318074d92b")
}