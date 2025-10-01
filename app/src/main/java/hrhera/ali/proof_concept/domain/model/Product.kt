package hrhera.ali.proof_concept.domain.model

import android.os.Parcelable

data class Product(
    val id: Int,
    val name: String,
    val price: Float,
    val image: String,
    val rating: Float,
    val stock: Int,
)