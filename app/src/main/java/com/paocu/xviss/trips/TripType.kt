package com.paocu.xviss.trips

import kotlin.reflect.KFunction5

class TripType(
    val title: String,
    val description: String,
    val image: String,
    val clickFunction: KFunction5<String, String, String, String, Class<*>, Unit>
)