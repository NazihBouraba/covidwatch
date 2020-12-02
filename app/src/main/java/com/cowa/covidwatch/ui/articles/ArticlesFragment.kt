package com.cowa.covidwatch.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cowa.covidwatch.MainActivity
import com.cowa.covidwatch.R
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.model.DataProviderSeance
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ArticlesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var articlesViewModel: ArticlesViewModel

    private val url = "https://corona-watch-esi.herokuapp.com/"
    var articleList = DataProviderSeance.data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articlesViewModel =
            ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_articles, container, false)
        setupRecyclerView(root)
        root.swipe_container_articles.setOnRefreshListener(this)

        return root
    }

    private fun setupRecyclerView(rootView: View) {

        getArticleList()
        val recyclerView = rootView.findViewById(R.id.articles_recyclerview) as RecyclerView

        val adapter = ArticlesRecyclerAdapter(context, articleList)
        recyclerView.adapter = adapter

        val manager = LinearLayoutManager(activity)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
    }

//    override fun displayArticleDetails(article: Article) {
//        val intent = Intent(context, ArticleViewActivity::class.java)
//        intent.putExtra("article",article)
//        intent.putExtra("titreArticle", article.title)
//       // intent.putExtra("soustitreArticle",article.subtitle)
//        intent.putExtra("contentArticle",article.content)
//       // intent.putExtra("image",article.images[0])
//        startActivity(intent)
//    }


    fun getArticleList() {

        //make progress spinner visible
        if (progressBar1 != null)
            progressBar1.visibility = View.VISIBLE
        //creer une instance du client retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        //creer une instance du service
        val service = retrofit.create(DataService::class.java)

        // creer la requete get
        val ArticleRequest = service.listarticles()


        // executer la requete get
        ArticleRequest.enqueue(object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                if (progressBar1 != null)
                    progressBar1.visibility = View.GONE
                val allarticles = response.body()
                if (allarticles != null) {
                    articleList = allarticles
                    var newadapter = ArticlesRecyclerAdapter(context, articleList)
                    articles_recyclerview.swapAdapter(newadapter, true)


                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                if (progressBar1 != null)
                    progressBar1.visibility = View.GONE
                Toast.makeText(MainActivity.context, "error getting articles", Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        getArticleList()
        swipe_container_articles.isRefreshing = false
    }
}
