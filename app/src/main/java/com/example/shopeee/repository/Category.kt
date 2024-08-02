package com.example.shopeee.repository

sealed class Category(val category: String) {
    object Burger: Category("Snacks")
    object Burger: Category("Burger")
    object Drinks: Category("Drinks")
    object Ramen: Category("Ramen")
    object Snacks: Category("Snacks")
    object Supplements: Category("Supplements")
}