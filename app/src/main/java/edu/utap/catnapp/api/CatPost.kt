package edu.utap.catnapp.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CatPost (
    // TODO: fix breeds implementation
    @SerializedName("breeds")
//    @Expose
    val breeds: List<Breed>,
    @SerializedName("categories")
//    @Expose
    val categories: List<Category>,
    @SerializedName("height")
//    @Expose
    val height: Int,
    @SerializedName("id")
//    @Expose
    val id: String,
    @SerializedName("url")
//    @Expose
    val url: String,
    @SerializedName("width")
//    @Expose
    val width: Int
    )


// this messes up the serializer - need to find new way

data class Category (
    @SerializedName("id")
    val breeds: Int,
    @SerializedName("name")
    val categories: String
)

data class Breed (
    //TODO: finish this
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
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
    // TODO: weight is an array, fix
    @SerializedName("weight")
    val weight: List<Weight>,
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
    @SerializedName("suppressed_tail")
    val suppressed_tail: Int,
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
    val vocalisation: Int,
    @SerializedName("cfa_url")
    val cfa_url: String,
    @SerializedName("country_codes")
    val country_codes: String,
    @SerializedName("indoor")
    val indoor: Int,
    @SerializedName("lap")
    val lap: Int,
    @SerializedName("vcahospitals_url")
    val vcahospitals_url: String,
    @SerializedName("vetstreet_url")
    val vetstreet_url: String
)

data class Weight (
    @SerializedName("imperial")
    val imperial: String,
    @SerializedName("metric")
    val metric: String
)