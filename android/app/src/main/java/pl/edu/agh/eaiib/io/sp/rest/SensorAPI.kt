package pl.edu.agh.eaiib.io.sp.rest

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.common.SensorData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SensorApi {

    @POST("sensorData")
    fun addSensorData(@Body sensorData: SensorData): Flowable<Any>

    companion object Factory {
        fun create(serverBaseUrl: String): SensorApi {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(serverBaseUrl)
                    .build()

            return retrofit.create(SensorApi::class.java)
        }
    }
}

