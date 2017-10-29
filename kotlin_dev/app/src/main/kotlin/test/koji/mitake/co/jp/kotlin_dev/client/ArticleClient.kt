package test.koji.mitake.co.jp.kotlin_dev.client

import rx.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import test.koji.mitake.co.jp.kotlin_dev.model.Article

/**
 * Created by KojiMitake on 2017/10/28.
 */


interface ArticleClient {

    @GET("/api/v2/items")

    fun search(@Query("query") query: String): Observable<List<Article>>

}