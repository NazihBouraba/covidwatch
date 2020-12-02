package com.cowa.covidwatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.model.Comment
import com.cowa.covidwatch.model.Video
import com.cowa.covidwatch.model.YtVideo
import com.cowa.covidwatch.ui.articles.ArticleViewActivity
import com.cowa.covidwatch.ui.netnews.DailyMotionActivity
import com.cowa.covidwatch.ui.netnews.YoutubeActivity
import com.cowa.covidwatch.ui.videos.VideoViewActivity
import com.cowa.covidwatch.util.Communicator

class MainActivity : AppCompatActivity(),Communicator {


    companion object{
      lateinit var  context: Context

    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private  var i : Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainActivity.context=this
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(

            R.id.nav_maps,R.id.nav_news,R.id.nav_videos,R.id.nav_signal,R.id.nav_my_health,R.id.nav_about,R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun displayArticleDetails(article: Article) {
        val intent = Intent(this, ArticleViewActivity::class.java)
        intent.putExtra("article",article)
        startActivity(intent)
    }

    override fun displayVideoDetails(video: Video) {
        val intent = Intent(this, VideoViewActivity::class.java)
        intent.putExtra("video",video)
        startActivity(intent)
    }
    override fun displayYtVideoDetails(ytvideo: YtVideo) {
        val intent = Intent(this, YoutubeActivity::class.java)
        intent.putExtra("ytvideo",ytvideo)
        startActivity(intent)
    }
    override fun displayDMVideoDetails(ytvideo: YtVideo) {
        val intent = Intent(this, DailyMotionActivity::class.java)
        intent.putExtra("DMvideo",ytvideo)
        startActivity(intent)
    }
}
