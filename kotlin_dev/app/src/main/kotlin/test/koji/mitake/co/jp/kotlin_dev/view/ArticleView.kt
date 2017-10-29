package test.koji.mitake.co.jp.kotlin_dev.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import test.koji.mitake.co.jp.kotlin_dev.R
import test.koji.mitake.co.jp.kotlin_dev.bindView
import test.koji.mitake.co.jp.kotlin_dev.model.Article

/**
 * Created by KojiMitake on 2017/10/23.
 */

class ArticleView: FrameLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    /*
    var profileImageView: ImageView? = null

    var titleTextView: TextView? = null

    var userNameTextView: TextView? = null
*/
/*    val profileImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.profile_image_view)
    }

    val titleTextView: TextView by lazy {
        findViewById<TextView>(R.id.title_text_view)
    }

    val userNameTextView: TextView by lazy{
        findViewById<TextView>(R.id.user_name_text_view)
    }
*/
    val profileImageView: ImageView by bindView<ImageView>(R.id.profile_image_view)

    val titleTextView: TextView by bindView<TextView>(R.id.title_text_view)

    val userNameTextView: TextView by bindView<TextView>(R.id.user_name_text_view)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_article, this)
    }

    fun setArticle(article: Article){

        titleTextView.text = article.title
        userNameTextView.text = article.user.name
        Glide.with(context).load(article.user.profileImageUrl).into(profileImageView)
        //profileImageView.setBackgroundColor(Color.RED)
    }

}
