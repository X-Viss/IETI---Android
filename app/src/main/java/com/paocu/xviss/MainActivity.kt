package com.paocu.xviss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.paocu.xviss.trips.TripAdapter
import com.paocu.xviss.trips.TripType
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        val urlActivity =
            "https://raw.githubusercontent.com/PaoCuellar/data_test/main/data_activity"
        getRequest(::activityLoad, urlActivity, queue)

        val urlTrip = "https://raw.githubusercontent.com/PaoCuellar/data_test/main/data_trip"
        getRequest(::tripLoad, urlTrip, queue)

        // Get the widgets reference
        Glide.with(applicationContext).load("https://picsum.photos/1280/720?random=100")
            .into(imageView)

        LoginButton.setOnClickListener {
            Toast.makeText(
                this,
                "Lo más popular",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this, GraphicsActivity::class.java))
        }
    }

    private fun activityLoad(response: JSONArray) {
        Log.d("GET REQUEST", "Response: %s".format(response.toString()))

        val activitiesList = ArrayList<Activity>()

        for (i in 0 until response.length()) {
            activitiesList.add(
                Activity(
                    response.getJSONObject(i).get("title") as String,
                    response.getJSONObject(i).get("description") as String,
                    response.getJSONObject(i).get("img") as String,
                    response.getJSONObject(i).get("location") as String
                )
            )
        }

        // Activities load
        Activities.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        Activities.adapter = ActivitiesAdapter(activitiesList)
    }

    private fun tripLoad(response: JSONArray) {
        Log.d("GET REQUEST", "Response: %s".format(response.toString()))

        val tripsList = ArrayList<TripType>()

        for (i in 0 until response.length()) {
            tripsList.add(
                TripType(
                    response.getJSONObject(i).get("location") as String,
                    response.getJSONObject(i).get("description") as String,
                    response.getJSONObject(i).get("img") as String
                )
            )
        }

        // Trip load
        Trip.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        Trip.adapter = TripAdapter(tripsList)
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
}