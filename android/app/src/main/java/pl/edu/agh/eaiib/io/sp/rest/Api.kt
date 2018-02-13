package pl.edu.agh.eaiib.io.sp.rest

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.common.model.Reading
import pl.edu.agh.eaiib.io.sp.common.model.ReadingsBatch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("readings")
    fun addReading(@Body reading: Reading): Flowable<Any>

    @POST("readingsBatch")
    fun addReadingsBatch(@Body readingsBatch: ReadingsBatch): Flowable<Any>

    @POST("comments")
    fun addComment(@Body comment: Comment): Flowable<Any>

    companion object Factory {
        fun create(serverBaseUrl: String): Api {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(serverBaseUrl)
                    .build()

            return retrofit.create(Api::class.java)
        }
    }
}

