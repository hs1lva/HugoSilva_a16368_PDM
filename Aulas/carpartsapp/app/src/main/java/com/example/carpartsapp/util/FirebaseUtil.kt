package com.example.carpartsapp.util

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtil {
    val db by lazy { FirebaseFirestore.getInstance() }
}
