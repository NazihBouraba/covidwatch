package com.cowa.covidwatch.util

import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.model.Video
import com.cowa.covidwatch.model.YtVideo

interface Communicator {
    fun displayArticleDetails(article: Article)
    fun displayVideoDetails(video : Video)
    fun displayYtVideoDetails(ytvideo : YtVideo)
    fun displayDMVideoDetails(ytvideo : YtVideo)
}