package com.example.modaurbanaapp.model

data class Product(
    val id: String,
    val name: String,
    val price: Int,        // en CLP
    val oldPrice: Int? = null,
    val imageUrl: String,
    val category: Category
)

enum class Category { Tees, Hoodies, Pants, Knits, Accessories }