package com.example.collegemanagement.Models

import com.google.firebase.Timestamp

data class NoticeModel(
    val imageUrl: String?="",
    val title : String?= "",
    val link : String?= "",
    val docId : String?= "",
    val timestamp: Timestamp? = null

)