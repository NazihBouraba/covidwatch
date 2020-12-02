package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: Int,
    val title: String,
    val user: Writer,
    val timestamp: String,
    val comments: List<Comment>,
    val description : String,
    val video : String



): Parcelable

object DataProviderVideo {

    val data: List<Video>
        get() {


            return ArrayList()
        }

}