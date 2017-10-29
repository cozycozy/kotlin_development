package test.koji.mitake.co.jp.kotlin_dev

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.Toast

/**
 * Created by KojiMitake on 2017/10/23.
 */

fun<T: View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
    findViewById<T>(id)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}