package daniel.id.candidates.core.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun <T> AppCompatActivity.toActivity(
    bundle: Bundle? = null, destination: Class<T>
) {
    val intent = Intent(baseContext, destination)
    if (bundle != null) intent.putExtras(bundle)
    startActivity(intent)
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).apply(
        RequestOptions().diskCacheStrategy(
        DiskCacheStrategy.ALL)).into(this)
}

//  is network connected
fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNet: NetworkInfo?
    return if (cm != null) {
        activeNet = cm.activeNetworkInfo
        activeNet != null && activeNet.isConnected
    } else
        false
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}