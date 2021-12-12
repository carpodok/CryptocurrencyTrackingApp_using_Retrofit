package com.alitalhacoban.cryptocurrencytrackingapp.service

import com.alitalhacoban.cryptocurrencytrackingapp.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    // 8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6

    @GET("currencies/ticker?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6")

   // @GET("coins")
  // @GET("prices?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6")
    fun getData(): Call<List<CryptoModel>>


    /*@GET("tickers")
    fun getPrice():Call<String>*/
}