package com.example.collegemanagement.Viewmodel

import androidx.lifecycle.ViewModel
import com.example.collegemanagement.Utils.Constants
import com.google.firebase.auth.FirebaseAuth

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

    fun signup(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Constants.isAdmin = email == "sakshamm1124@gmail.com"
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Signup Failed")
            }
    }

    fun logout(onSuccess: () -> Unit) {
        auth.signOut()
        onSuccess()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
