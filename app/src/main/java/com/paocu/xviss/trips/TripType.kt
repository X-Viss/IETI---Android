package com.paocu.xviss.trips

import kotlin.reflect.KFunction3

class TripType(
    val title: String,
    val description: String,
    val image: String,
    val clickFunction: KFunction3<String, String, String, Unit>
)