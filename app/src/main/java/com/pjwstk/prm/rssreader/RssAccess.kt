package com.pjwstk.prm.rssreader

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RssAccess {

    val okHttpClient = OkHttpClient.Builder().build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://www.polsatsport.pl/rss/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
}