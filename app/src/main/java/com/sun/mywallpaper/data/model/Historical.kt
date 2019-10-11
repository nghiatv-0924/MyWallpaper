package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class Historical(
    @SerializedName(JSON_KEY_CHANGE) val change: Int,
    @SerializedName(JSON_KEY_RESOLUTION) val resolution: String,
    @SerializedName(JSON_KEY_QUANTITY) val quantity: Int,
    @SerializedName(JSON_KEY_VALUES) val values: List<Value>
) {
    data class Value(
        @SerializedName(JSON_KEY_VALUES_DATE) val date: String,
        @SerializedName(JSON_KEY_VALUES_VALUE) val value: Int
    )

    companion object {
        private const val JSON_KEY_CHANGE = "change"
        private const val JSON_KEY_RESOLUTION = "resolution"
        private const val JSON_KEY_QUANTITY = "quantity"
        private const val JSON_KEY_VALUES = "values"
        private const val JSON_KEY_VALUES_DATE = "date"
        private const val JSON_KEY_VALUES_VALUE = "value"
    }
}
