package test.koji.mitake.co.jp.kotlin_dev

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import test.koji.mitake.co.jp.kotlin_dev.model.Article
import test.koji.mitake.co.jp.kotlin_dev.view.ArticleView

/**
 * Created by koji_mitake on 2017/10/24.
 */


class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val articleView = findViewById(R.id.article_view) as ArticleView
        val webView = findViewById(R.id.web_view) as WebView

        val article: Article = intent.getParcelableExtra(ARTICLE_EXTRA)
        articleView.setArticle(article)
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl(article.url)

    }

    companion object {

        private const val ARTICLE_EXTRA: String= "article"

        fun intent(context: Context, article: Article): Intent =
                Intent(context, ArticleActivity::class.java)
                .putExtra(ARTICLE_EXTRA, article)

    }




}