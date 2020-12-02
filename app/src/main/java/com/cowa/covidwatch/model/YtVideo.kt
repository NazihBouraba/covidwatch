package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YtVideo (
    val youtube_id: String,
    val id : Int,
    val title: String,
    val timestamp: String,
    val embed_link : String
)
    : Parcelable
{
    object DataProviderytVideo {

        val data: List<YtVideo>
            get() {


                return ArrayList()
            }

    }
}