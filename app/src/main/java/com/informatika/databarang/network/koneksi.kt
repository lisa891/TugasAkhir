package com.informatika.databarang.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class koneksi {
    companion object{
        val baseUrl = "http://10.10.3.54/daba/r/api"
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var service: ApiService = retrofit.create(ApiService::class.java)
    }
}