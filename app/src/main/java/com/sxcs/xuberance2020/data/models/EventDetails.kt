package com.sxcs.xuberance2020.data.models

import java.io.Serializable

data class EventDetails(
    var date: String = "",
    var day: Int = 0,
    var imageUrl: String = "",
    var meaning: String = "",
    var name: String = "",
    var numberPart: Int = 0,
    var rules: String = "",
    var shortDesc: String = "",
    var slno: Int = -1,
    var time: String = "",
    var type: String = ""
) : Serializable