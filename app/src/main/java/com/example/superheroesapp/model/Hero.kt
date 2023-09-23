package com.example.superheroesapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @StringRes val nameRes: Int,
    val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)
