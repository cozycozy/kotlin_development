package test.koji.mitake.co.jp.kotlin_dev

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import test.koji.mitake.co.jp.kotlin_dev.model.Article
import test.koji.mitake.co.jp.kotlin_dev.model.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext)

        listAdapter.articles = listOf(dummyArticle("kotolin", "koji"),dummyArticle("java","takeshi"))

        val listview : ListView = findViewById(R.id.list_view) as ListView

        listview.adapter = listAdapter

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
                    url = "http://google.com",
                    user = User(id = "", name = userName,profileImageUrl = ""))
}
