package com.example.collegemanagement.Viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.CollegeInfoModel
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.Utils.Constants.BANNER
import com.example.collegemanagement.Utils.Constants.COLLEGE_INFO
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class CollegeInfoViewModel : ViewModel() {

    private val collegeInfoRef = Firebase.firestore.collection(COLLEGE_INFO)

    private val storageRef = Firebase.storage.reference

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted

    private val _collegeInfo = MutableLiveData<CollegeInfoModel>()
    val collegeInfo : LiveData<CollegeInfoModel> = _collegeInfo



    fun saveImage(uri: Uri,name:String, address:String, desc:String, websiteLink:String){
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$COLLEGE_INFO/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                uploadImage(it.toString(),name,address,desc,websiteLink)
            }
        }
    }

     fun uploadImage(imageUrl: String,name:String, address:String, desc:String, websiteLink:String) {

        val map = mutableMapOf<String,Any>()
        map["imageUrl"] = imageUrl
        map["websiteLink"] = websiteLink
        map["name"] = name
        map["desc"] = desc
        map["address"] = address


        collegeInfoRef.document("Nit_Jamshedpur").set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)

            }
    }

    fun getCollegeInfo(){
        collegeInfoRef.document("Nit_Jamshedpur").get().addOnSuccessListener {

            _collegeInfo.postValue(
                CollegeInfoModel(
                    it.data!!["name"].toString(),
                    it.data!!["address"].toString(),
                    it.data!!["desc"].toString(),
                    it.data!!["websiteLink"].toString(),
                    it.data!!["imageUrl"].toString()
                )
            )


        }
    }



}
