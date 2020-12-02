package com.cowa.covidwatch.ui.netnews

import android.os.Bundle
import android.widget.Toast
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.YtVideo
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_youtube.*


class YoutubeActivity :  YouTubeBaseActivity() , YouTubePlayer.OnInitializedListener{

    lateinit var ytvideo : YtVideo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)


        ytvideo = intent.getParcelableExtra<YtVideo>("ytvideo")
        playVideo(ytvideo.youtube_id, youtubePlayerView)
    }

    fun playVideo( videoId : String, youTubePlayerView  : YouTubePlayerView){

        youTubePlayerView.initialize("AIzaSyBcTMN_jWUvaPECzmnAzPym2mGppvWKGnI",this)

    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {
            p1?.cueVideo(ytvideo.youtube_id)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if (p1 != null) {
            if (p1.isUserRecoverableError()) {

                p1.getErrorDialog(this, 1).show()
            } else {
                Toast.makeText(this, "Eroor playing video from youtube", Toast.LENGTH_LONG).show()
            }
        }
    }


}
