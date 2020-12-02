package com.cowa.covidwatch.service

import com.cowa.covidwatch.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface DataService {

    //get user info for authentication
    // we need to figure out the parametres of this function
    @GET("/user/authen") fun user(): Call<User>

    @FormUrlEncoded
    @POST("/auth/convert-token") fun signin(@Field("token") content:String): Call<Boolean?>?

    // get articles from server
    @GET("/content/articles-verified/") fun listarticles(): Call<List<Article>>

    // get videos from server
    @GET("/content/videos-verified/") fun listvideo(): Call<List<Video>>


    // get youtube  videos from server
    @GET("/scrapping/youtube-videos-verified/") fun youtube_video_list(): Call<List<YtVideo>>

      // get daily motion videos
    @GET("/scrapping/dailymotion-videos-verified/") fun daily_motion_video_list(): Call<List<DMvideo>>

    @GET("/video/{id}?fields=thumbnail_medium_url") fun thumbnail(@Path("id") id :String ): Call<DMThumbnail>





    // make article comment
    // it returns the added comment
    @POST("/content/comments/") fun makecommentTmp(@Body comment: PostCommentBody): Call<Comment>?
//    @POST("/user/comment") fun makecomment(@Body user: User?,article : Article, comment : String): Call<Boolean?>?

    @POST("/auth/convert-token/") fun facbookauthen(@Body t: Token): Call<TokenAnswer>?










}