package com.cowa.covidwatch.ui.netnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.YtVideo
import kotlinx.android.synthetic.main.activity_daily_motion.*
import kotlinx.android.synthetic.main.activity_youtube.*

class DailyMotionActivity : AppCompatActivity() {
    override fun onPause() {
        super.onPause()
        dm_player_web_view.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_motion)

       val ytvideo = intent.getParcelableExtra<YtVideo>("DMvideo")
        dm_player_web_view.load(ytvideo.youtube_id)

    }
}
