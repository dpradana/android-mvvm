package daniel.id.candidates.core.network

import android.app.Application
import daniel.id.candidates.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient(val application: Application) {

    fun service(): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(createClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(Api::class.java)
    }

    fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        okHttpClientBuilder.writeTimeout(1200, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(1200, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(1200, TimeUnit.SECONDS)

//        val token = application.applicationContext.getPref(Constants.token)
//
//        okHttpClientBuilder.addInterceptor { chain ->
//            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .build()
//            chain.proceed(request)
//        }
        return okHttpClientBuilder.build()
    }
}