package com.example.collegemanagement.Models


data class BannerModel(
    val imageUrl: String? = null,
    val docId: String? = null,
    val expiryTime: com.google.firebase.Timestamp? = null,
    val isAutoExpire: Boolean? = null
){
    fun toMap(): Map<String, Any?> = mapOf(
        "imageUrl" to imageUrl,
        "docId" to docId,
        "expiryTime" to expiryTime,
        "isAutoExpire" to isAutoExpire
    )
}