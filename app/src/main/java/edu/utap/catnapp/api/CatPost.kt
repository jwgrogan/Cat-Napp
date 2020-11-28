package edu.utap.catnapp.api

import com.google.gson.annotations.SerializedName

data class CatPost (
    @SerializedName("breeds")
    val breeds: Breed,
    @SerializedName("categories")
    val categories: Category,
    @SerializedName("height")
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
    val categories: String,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("life_span")
    val life_span: String,
    @SerializedName("alt_names")
    val alt_names: String,
    @SerializedName("wikipedia_url")
    val wikipedia_url: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("weight_imperial")
    val weight_imperial: String,
    @SerializedName("experimental")
    val experimental: Int,
    @SerializedName("hairless")
    val hairless: Int,
    @SerializedName("natural")
    val natural: Int,
    @SerializedName("rare")
    val rare: Int,
    @SerializedName("rex")
    val rex: Int,
    @SerializedName("suppress_tail")
    val suppress_tail: Int,
    @SerializedName("short_legs")
    val short_legs: Int,
    @SerializedName("hypoallergenic")
    val hypoallergenic: Int,
    @SerializedName("adaptability")
    val adaptability: Int,
    @SerializedName("affection_level")
    val affection_level: Int,
    @SerializedName("country_code")
    val country_code: String,
    @SerializedName("child_friendly")
    val child_friendly: Int,
    @SerializedName("dog_friendly")
    val dog_friendly: Int,
    @SerializedName("energy_level")
    val energy_level: Int,
    @SerializedName("grooming")
    val grooming: Int,
    @SerializedName("health_issues")
    val health_issues: Int,
    @SerializedName("intelligence")
    val intelligence: Int,
    @SerializedName("shedding_level")
    val shedding_level: Int,
    @SerializedName("social_needs")
    val social_needs: Int,
    @SerializedName("stranger_friendly")
    val stranger_friendly: Int,
    @SerializedName("vocalisation")
    val vocalisation: Int
)