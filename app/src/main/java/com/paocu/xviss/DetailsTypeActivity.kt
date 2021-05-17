package com.paocu.xviss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.paocu.xviss.activities.ActivitiesAdapter
import com.paocu.xviss.activities.Activity
import kotlinx.android.synthetic.main.activity_details_type.*
import org.json.JSONArray

class DetailsTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_type)


        val queue = Volley.newRequestQueue(this)

        // Get the Intent that started this activity and extract the string
        activityTitle.text = intent.getStringExtra(VIEW_TITLE)
        activityDescription.text = intent.getStringExtra(VIEW_DESCRIPTION)
        activityLocation.text = intent.getStringExtra(VIEW_LOCATION)
        Glide.with(applicationContext).load(intent.getStringExtra(IMG_VIEW))
            .into(activityImage)


        val urlActivity =
            "https://raw.githubusercontent.com/PaoCuellar/data_test/main/data_activity"
        getRequest(::activityLoad, urlActivity, queue)

    }

    private fun activityLoad(response: JSONArray) {
        val activitiesList = ArrayList<Activity>()

        for (i in 0 until response.length()) {
            activitiesList.add(
                Activity(
                    response.getJSONObject(i).get("title") as String,
                    response.getJSONObject(i).get("description") as String,
                    response.getJSONObject(i).get("img") as String,
                    response.getJSONObject(i).get("location") as String,
                    ::switchActivities
                )
            )
        }

        // Activities load
        ListActivity.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        ListActivity.adapter = ActivitiesAdapter(activitiesList)
    }

    private fun messageError(error: VolleyError) {
        Log.e("Network issue", error.message.toString())
        Toast.makeText(
            applicationContext,
            "Error no encontramos una red disponible",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getRequest(function: (r: JSONArray) -> Unit, url: String, queue: RequestQueue) {
        // Request a object response from the provided URL.
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                function(response)
            },
            { error ->
                messageError(error)
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun switchActivities(title: String, image: String, description: String, location:String, activity:Class<*>) {
        val intent = Intent(this, activity).apply {
            putExtra(VIEW_TITLE, title)
            putExtra(IMG_VIEW, image)
            putExtra(VIEW_DESCRIPTION, description)
            putExtra(VIEW_LOCATION,location)
        }
        // start your next activity
        startActivity(intent)
    }
}