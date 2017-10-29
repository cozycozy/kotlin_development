package test.koji.mitake.co.jp.kotlin_dev

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
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

        val listAdapter = ArticleListAdapter(applicationContext)

        val listview : ListView = findViewById(R.id.list_view) as ListView
        val queryEditText = findViewById(R.id.query_edit_text) as EditText
        val searchButton = findViewById(R.id.search_button) as Button

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

        val articleClient = retrofit.create((ArticleClient::class.java))

        val progressBar = findViewById(R.id.progress_bar) as ProgressBar
            progressBar.visibility = View.VISIBLE

            articleClient.search("android")
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
                        toast("エラー：$it")
                    })


        searchButton.setOnClickListener {

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
                        toast("エラー：$it")
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

