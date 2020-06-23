package com.pjwstk.prm.rssreader

import com.pjwstk.prm.rssreader.model.RssJava
import retrofit2.Call
import retrofit2.http.GET

interface FeedRepository{
    @GET("pilkanozna.xml")
    fun getFeeds(): Call<RssJava>
}