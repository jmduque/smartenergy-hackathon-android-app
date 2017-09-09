package com.energolabs.evergo.requests

import android.content.Context
import com.energolabs.evergo.BuildConfig
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.ledger.models.JsonDeserializer.TransactionWrapperModelJsonDeserializer
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class EnergoRetrofitCallBuilder private constructor(context: Context) {

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    init {
        // WE NEED AN INTERCEPTOR TO ADD COMMON HEADERS TO ALL HTTP REQUESTS
        val interceptor = Interceptor { chain ->
            val authPreferences = AuthPreferences(context)
            val original = chain.request()
            val request = original.newBuilder()
                    .header(
                            "Accept",
                            "application/json"
                    ).header(
                    "Authorization",
                    authPreferences.accessToken
            ).header(
                    "Content-Type",
                    "application/json"
            ).method(
                    original.method(),
                    original.body()
            ).build()
            chain.proceed(request)
        }

        // START HTTP CLIENT BUILDER
        val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(
                        interceptor
                )

        // FOR DEBUG PROPOSES, WE ADD A LOG INTERCEPTOR
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(
                    httpLoggingInterceptor
            )
        }

        // FINALIZE CLIENT BUILDING
        val okHttpClient = okHttpClientBuilder.build()
        retrofitBuilder.addConverterFactory(
                GsonConverterFactory.create(
                        GsonBuilder().setFieldNamingPolicy(
                                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                        ).registerTypeAdapter(
                                TransactionWrapperModel::class.java,
                                TransactionWrapperModelJsonDeserializer()
                        ).create()
                )
        )
        retrofitBuilder.client(okHttpClient)
        baseUrl()
    }

    private fun baseUrl() {
        retrofitBuilder.baseUrl(
                "http://ec2-54-169-214-62.ap-southeast-1.compute.amazonaws.com:3001/api/v1/"
        )
    }

    private fun build(): Retrofit {
        return retrofitBuilder.build()
    }

    companion object {

        fun <S> createService(
                context: Context,
                serviceClass: Class<S>
        ): S {
            val energoRetrofitCallBuilder = EnergoRetrofitCallBuilder(context)
            val retrofit = energoRetrofitCallBuilder.build()
            return retrofit.create(serviceClass)
        }
    }

}
