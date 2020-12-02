package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DMvideo (
    val id : Int ,
    val timestamp: String ,
    val title : String,
    val dailymotion_id : String,
    val embed_link : String

): Parcelable{
    object DMProviderVideo {

        val data: List<DMvideo>
            get() {


                return ArrayList()
            }

    }
}