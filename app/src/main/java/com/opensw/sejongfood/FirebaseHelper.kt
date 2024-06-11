package com.opensw.sejongfood

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class FirebaseHelper(
    private val context: Context
) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addPlaceData(placeData: PlaceData) {
        db.collection("places")
            .get()
            .addOnSuccessListener { documents ->
                val nextIndex = documents.size() + 1
                db.collection("places")
                    .document(nextIndex.toString())
                    .set(placeData)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener {
                        error()
                    }
            }
            .addOnFailureListener {
                error()
            }
    }

    fun getAllPlaceData(onComplete: (List<PlaceData>) -> Unit) {
        db.collection("places")
            .get()
            .addOnSuccessListener { documents ->
                val placeList = documents.toObjects<PlaceData>()
                onComplete(placeList)
            }
            .addOnFailureListener {
                error()
                onComplete(emptyList())
            }
    }

    fun getPlaceDataByIndex(index: Int, onComplete: (PlaceData?) -> Unit) {
        db.collection("places")
            .whereEqualTo("index", index)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onComplete(null)
                } else {
                    val placeData = documents.documents[0].toObject<PlaceData>()
                    onComplete(placeData)
                }
            }
            .addOnFailureListener {
                error()
                onComplete(null)
            }
    }

    fun addReviewToPlace(index: Int, review: Review, onComplete: () -> Unit) {
        val placeDocRef = db.collection("places").document(index.toString())

        placeDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val placeData = document.toObject(PlaceData::class.java)
                placeData?.let {
                    val updatedReviews = it.review.toMutableList()
                    updatedReviews.add(review)
                    placeDocRef.update("review", updatedReviews)
                        .addOnSuccessListener {
                            onComplete()
                        }
                        .addOnFailureListener { e ->
                            error()
                            onComplete()
                        }
                }
            } else {
                error()
                onComplete()
            }
        }
    }

    private fun error() {
        Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show()
    }
}
