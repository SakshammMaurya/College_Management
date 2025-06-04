package com.example.collegemanagement.Viewmodel

import androidx.lifecycle.ViewModel
import com.example.collegemanagement.Utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Constants.isAdmin = email == "sakshamm1124@gmail.com"
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Login Failed")
            }
    }

    fun signup2(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Constants.isAdmin = email == "sakshamm1124@gmail.com"
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Signup Failed")
            }
    }
    fun signup(
        email: String,
        password: String,
        name: String,
        number: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                // Save user info to Firestore
                val user = hashMapOf(
                    "uid" to userId,
                    "email" to email,
                    "name" to name,
                    "number" to number
                )

                FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .set(user)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onError("Failed to save user info: ${e.message}")
                    }
            }
            .addOnFailureListener {
                onError(it.message ?: "Sign up failed")
            }
    }


    fun logout(onSuccess: () -> Unit) {
        auth.signOut()
        onSuccess()
    }


    fun fetchUserProfile(onResult: (String, String) -> Unit, onError: (String) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onError("User not logged in")

        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val name = document.getString("name") ?: "N/A"
                val number = document.getString("number") ?: "N/A"
                onResult(name, number)
            }
            .addOnFailureListener {
                onError("Failed to fetch user data")
            }
    }



}
