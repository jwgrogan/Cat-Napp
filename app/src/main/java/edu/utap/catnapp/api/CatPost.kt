package edu.utap.catnapp.api

import com.google.gson.annotations.SerializedName

data class CatPost (
    @SerializedName("breeds")
    val breeds: List<Breed>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("hieght")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
    )

data class Category (
    @SerializedName("id")
    val breeds: Int,
    @SerializedName("name")
    val categories: String
)

data class Breed (
    //TODO: finish this
    @SerializedName("id")
    val breeds: String,
    @SerializedName("name")
    val categories: String
)