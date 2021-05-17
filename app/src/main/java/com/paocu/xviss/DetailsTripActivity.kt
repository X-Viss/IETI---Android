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
import com.paocu.xviss.trips.TripAdapter
import com.paocu.xviss.trips.TripType
import kotlinx.android.synthetic.main.activity_details_trip.*
import org.json.JSONArray

class DetailsTripActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_trip)

        val queue = Volley.newRequestQueue(this)

        // Get the Intent that started this activity and extract the string
        tripTitle.text = intent.getStringExtra(VIEW_TITLE)
        tripDescription.text = intent.getStringExtra(VIEW_DESCRIPTION)
        Glide.with(applicationContext).load(intent.getStringExtra(IMG_VIEW))
            .into(tripImage)

        val urlTrip = "https://raw.githubusercontent.com/PaoCuellar/data_test/main/data_trip"
        getRequest(::tripLoad, urlTrip, queue)

    }

    private fun tripLoad(response: JSONArray) {
        val tripsList = ArrayList<TripType>()

        for (i in 0 until response.length()) {
            tripsList.add(
                TripType(
                    response.getJSONObject(i).get("location") as String,
                    response.getJSONObject(i).get("description") as String,
                    response.getJSONObject(i).get("img") as String,
                    ::switchActivities
                )
            )
        }

        // Trip load
        Trips.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        Trips.adapter = TripAdapter(tripsList)
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