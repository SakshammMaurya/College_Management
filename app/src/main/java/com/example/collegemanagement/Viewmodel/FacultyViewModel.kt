package com.example.collegemanagement.Viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.FacultyModel
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.Utils.Constants.BANNER
import com.example.collegemanagement.Utils.Constants.FACULTY
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class FacultyViewModel : ViewModel() {

    private val facultyRef = Firebase.firestore.collection(FACULTY)

    private val storageRef = Firebase.storage.reference

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted : LiveData<Boolean> = _isPosted

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted : LiveData<Boolean> = _isDeleted

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    private val _facultyList = MutableLiveData<List<FacultyModel>>()
    val facultyList: LiveData<List<FacultyModel>> = _facultyList




    fun saveFaculty(uri: Uri, name:String, email:String, position:String , catName: String){
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$FACULTY/${randomUid}.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                uploadFaculty(it.toString(),randomUid, name,email,position, catName)
            }
        }
    }

    private fun uploadFaculty(imageUrl: String, docId:String, name: String,email:String, position:String, catName: String) {

        val map = mutableMapOf<String,String>()
        map["imageUrl"] = imageUrl
        map["docId"] = docId
        map["name"] = name
        map["email"] = email
        map["position"] = position
        map["catName"] = catName


        facultyRef.document(catName).collection("teacher").document(docId).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)

            }
    }

    fun uploadCategory(category:String) {

        val map = mutableMapOf<String,String>()
        map["catName"] = category

        facultyRef.document(category).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener{
                _isPosted.postValue(false)

            }
    }

    fun getFaculty(catName: String){
        facultyRef.document(catName).collection("teacher").get().addOnSuccessListener {
            val list = mutableListOf<FacultyModel>()

                for(doc in it){
                    list.add(doc.toObject(FacultyModel::class.java))
                }

            _facultyList.postValue(list)
        }
    }

    fun getCategory(){
        facultyRef.get().addOnSuccessListener {
            val list = mutableListOf<String>()

            for(doc in it){
                list.add(doc.get("catName").toString())
            }

            _categoryList.postValue(list)
        }
    }

    fun deleteFaculty(facultyModel: FacultyModel){



        facultyRef.document(facultyModel.catName!!).collection("teacher").document(facultyModel.docId!!).delete()
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(facultyModel.imageUrl!!).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener{
                _isDeleted.postValue(false)
            }
    }

    fun deleteCategory(category: String){



        facultyRef.document(category).delete()
            .addOnSuccessListener {
//                Firebase.storage.getReferenceFromUrl(facultyModel.imageUrl!!).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener{
                _isDeleted.postValue(false)
            }
    }

}
