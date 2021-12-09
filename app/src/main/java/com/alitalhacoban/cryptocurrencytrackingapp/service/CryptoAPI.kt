package com.alitalhacoban.cryptocurrencytrackingapp.service

import com.alitalhacoban.cryptocurrencytrackingapp.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    // 8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6

    // https://api.nomics.com/v1/currencies/ticker?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6

    // currencies/ticker?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6&ids=BTC,ETH,XRP&interval=1d,30d&convert=EUR&platform-currency=ETH&per-page=100&page=1


    @GET("currencies/ticker?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6")
   // @GET("prices?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6")
    fun getData(): Call<List<CryptoModel>>

    //@GET("currencies/ticker?key=8112b594d595e1e9d5136ccbcc0e03c5fb7aabd6&ids=BTC,ETH,XRP&interval=1d,30d&convert=EUR&platform-currency=ETH&per-page=100&page=1")

}