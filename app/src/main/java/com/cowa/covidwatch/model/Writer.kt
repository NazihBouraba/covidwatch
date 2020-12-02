package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Writer(
    val first_name : String,
    val last_name : String,
    val email : String

): Parcelable