package com.cowa.covidwatch.util

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


class StringUtils {
    companion object {
        fun stringTimeStampToTimeAgo(timestamp: String):String{
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sdf.timeZone = TimeZone.getDefault()
            val date:Date? = sdf.parse(timestamp)
            val p = PrettyTime(Locale("ar"))
            return p.format(date)
        }
    }
}