package test.koji.mitake.co.jp.kotlin_dev

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import test.koji.mitake.co.jp.kotlin_dev.model.Article
import test.koji.mitake.co.jp.kotlin_dev.view.ArticleView

/**
 * Created by KojiMitake on 2017/10/23.
 */

class ArticleListAdapter(private val context: Context): BaseAdapter(){

    var articles: List<Article> = emptyList()

    override fun getCount(): Int = articles.size

    override fun getItem(position: Int): Any? = articles[position]

    override fun getItemId(position: Int): Long =0

    override fun getView(position: Int,
                         convetView: View?,
                         parent: ViewGroup?): View =
            ((convetView as? ArticleView) ?: ArticleView(context)).apply {
                setArticle(articles[position])
            }
}