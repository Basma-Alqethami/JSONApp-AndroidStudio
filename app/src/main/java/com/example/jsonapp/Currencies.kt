package com.example.jsonapp

import com.google.gson.annotations.SerializedName

class Currencies {
    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Cur? = null

    class Cur {

        @SerializedName("inr")
        var inr: String? = null

        @SerializedName("usd")
        var usd: String? = null

        @SerializedName("aud")
        var aud: String? = null

        @SerializedName("sar")
        var sar: String? = null

        @SerializedName("cny")
        var cny: String? = null

        @SerializedName("jpy")
        var jpy: String? = null

    }
}