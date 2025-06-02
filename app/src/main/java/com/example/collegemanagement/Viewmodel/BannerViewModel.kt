package com.example.collegemanagement.Viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Utils.Constants.BANNER
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.Date
import java.util.UUID

class BannerViewModel : ViewModel() {

    private val bannerRef = Firebase.firestore.collection(BANNER)

    private val storageRef = Firebase.storage.reference

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted : LiveData<Boolean> = _isDeleted

    private val _bannerList = MutableLiveData<List<BannerModel>>()
    val bannerList: MutableLiveData<List<BannerModel>> = _bannerList


    fun saveImage(uri: Uri, expiryTime: Timestamp?, isAutoExpire: Boolean) {
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$BANNER/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                uploadImage(downloadUrl.toString(), randomUid, expiryTime, isAutoExpire)
//                uploadImage(downloadUrl.toString(), randomUid, expiryTime, true)
            }
        }.addOnFailureListener {
            _isPosted.postValue(false)
        }
    }


    private fun uploadImage(imageUrl: String, docId: String, expiryTime: Timestamp?, isAutoExpire: Boolean) {
        val map = mutableMapOf<String, Any>(
            "imageUrl" to imageUrl,
            "docId" to docId,
            "isAutoExpire" to isAutoExpire
        )

        expiryTime?.let {
            map["expiryTime"] = it
        }
        Log.d("UploadDebug", "Uploading with isAutoExpire = $isAutoExpire")

        bannerRef.document(docId).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }


    fun deleteExpiredBanners() {
        val currentTime = System.currentTimeMillis()
        bannerRef.whereLessThan("expiryTime", currentTime)
            .get().addOnSuccessListener { docs ->
                for (doc in docs) {
                    val imageUrl = doc.getString("imageUrl") ?: continue
                    Firebase.storage.getReferenceFromUrl(imageUrl).delete()
                    doc.reference.delete()
                }
            }
    }

    fun getBanner() {
        bannerRef.get().addOnSuccessListener { snapshot ->
            val currentTime = System.currentTimeMillis()
            val list = mutableListOf<BannerModel>()

            for (doc in snapshot) {
                try {
                    val banner = doc.toObject(BannerModel::class.java)
                    val expiry = banner.expiryTime?.toDate()?.time
                    val isExpired = banner.isAutoExpire == true && expiry != null && expiry < currentTime

                    Log.d("BannerDebug", "docId=${banner.docId}, isAutoExpire=${banner.isAutoExpire}, expiry=${expiry}, now=${currentTime}, isExpired=$isExpired")

                    if (isExpired) {
                        // Delete expired banner
                        bannerRef.document(banner.docId!!).delete()
                            .addOnSuccessListener {
                                Log.d("BannerDelete", "Deleted banner: ${banner.docId}")
                            }
                            .addOnFailureListener {
                                Log.e("BannerDelete", "Failed to delete banner: ${it.message}")
                            }

                        Firebase.storage.getReferenceFromUrl(banner.imageUrl!!).delete()
                            .addOnSuccessListener {
                                Log.d("ImageDelete", "Deleted image: ${banner.imageUrl}")
                            }
                            .addOnFailureListener {
                                Log.e("ImageDelete", "Failed to delete image: ${it.message}")
                            }
                    } else {
                        list.add(banner)
                    }
                } catch (e: Exception) {
                    Log.e("BannerParse", "Error parsing banner: ${e.message}")
                }
            }

            Log.d("BannerResult", "Final banner list size: ${list.size}")
            _bannerList.postValue(list)
        }
    }






    fun deleteBanner(bannerModel:BannerModel){

        bannerRef.document(bannerModel.docId!!).delete()
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(bannerModel.imageUrl!!).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener{
                _isDeleted.postValue(false)
            }
    }

}
