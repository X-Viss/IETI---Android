package com.paocu.xviss.activities

import kotlin.reflect.KFunction5

data class Activity(
    val title: String,
    val description: String,
    val image: String,
    val location: String,
    val clickFunction: KFunction5<String, String, String, String, Class<*>, Unit>
)