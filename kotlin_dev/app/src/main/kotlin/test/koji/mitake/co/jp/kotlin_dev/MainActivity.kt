package test.koji.mitake.co.jp.kotlin_dev

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import test.koji.mitake.co.jp.kotlin_dev.client.ArticleClient
import test.koji.mitake.co.jp.kotlin_dev.model.Article
import test.koji.mitake.co.jp.kotlin_dev.model.User

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listview : ListView = findViewById(R.id.list_view) as ListView
        val queryEditText = findViewById(R.id.query_eidt_text) as EditText
        val searchButton = findViewById(R.id.search_button) as Button
        val progressBar = findViewById(R.id.progress_bar) as ProgressBar

        val listAdapter = ArticleListAdapter(applicationContext)

        //listAdapter.articles = listOf(dummyArticle("kotolin", "koji"),dummyArticle("java","takeshi"))

        listview.adapter = listAdapter
        listview.setOnItemClickListener { adapterView, view, position, id ->
            var article = listAdapter.articles[position]
            ArticleActivity.intent(this,article).let { startActivity(it) }

        }

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        val articleClient = retrofit.create(ArticleClient::class.java)

        //SearchButtonのリスナ設定
        searchButton.setOnClickListener {

            //プログレスバーの表示
            progressBar.visibility = View.VISIBLE

            articleClient.search(queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate {
                        progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe({
                        queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    },{
                        toast("エラー: $it")
                    })
        }


        /*
        //ArticleViewオブジェクトの生成
        val articleView = ArticleView(applicationContext)

        // ArticleViewオブジェクトにセット
        articleView.setArticle(Article(id="123",
                title = "Kotlin",
                url = "http://yahoo.co.jp",
                user = User(id = "456", name = "test", profileImageUrl = "")))

        //AtricleViewオブジェクトをActivityにセット
        setContentView(articleView)
        */
    }

    private fun dummyArticle(title: String, userName: String): Article =
            Article(id = "",
                    title = title,
                    url = "https://google.com",
                    user = User(id = "", name = userName,profileImageUrl = ""))
}

