package edu.utap.catnapp.api

class CatRepository(private val api: CatApi) {
//    private fun unpackPosts(response: CatApi.ListingResponse): List<CatPost>? {
//        return response.data.children.map { it.data }
//    }

    suspend fun getNineCats(category: String): List<CatApi.CatResponse> {
        val result = api.getNineCats(category, "ebb49bcd-81c8-450f-9149-64318074d92b")
//        return unpackPosts(result)
        return result
    }

//    suspend fun getNineCats(difficulty : String) = api.getNineCats(categories)
}