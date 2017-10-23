package test.koji.mitake.co.jp.kotlin_dev

import android.support.annotation.IdRes
import android.view.View

/**
 * Created by KojiMitake on 2017/10/23.
 */

fun<T: View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
    findViewById<T>(id)
}