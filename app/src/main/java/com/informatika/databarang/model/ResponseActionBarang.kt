package com.informatika.databarang.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.annotations.SerializedName

data class ResponseActionBarang(
    @field:SerializedName ( "pesan")
    val pesan: Any? = null,

     @field:SerializedName ("data")
    val data: List<Boolean?>? = null,

     @field:SerializedName ("status")
    val status: String? = null
)

