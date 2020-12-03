package edu.utap.catnapp.api

class CatRepository(private val api: CatApi) {
    suspend fun getNineCats(category: String): List<CatPost> {
        return api.getNineCats(category, "ebb49bcd-81c8-450f-9149-64318074d92b")
    }
}