package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val content : String,
    val user:String?,
    val post:Int,
    val is_active: Boolean = true
):Parcelable