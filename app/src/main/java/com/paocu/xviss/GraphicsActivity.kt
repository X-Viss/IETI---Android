package com.paocu.xviss

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_graphics.*
import org.json.JSONObject


val COLORS = intArrayOf(
    ColorTemplate.rgb("#D244FA"),
    ColorTemplate.rgb("#8E3CDE"),
    ColorTemplate.rgb("#764EF5"),
    ColorTemplate.rgb("#3C42DE"),
    ColorTemplate.rgb("#457EFF"),
    ColorTemplate.rgb("#3399E8"),
    ColorTemplate.rgb("#38DCFF"),
    ColorTemplate.rgb("#27E8D8"),
    ColorTemplate.rgb("#2BFFB1"),
    ColorTemplate.rgb("#1CE864"),
    ColorTemplate.rgb("#1FFF2E"),
    ColorTemplate.rgb("#4AE810"),
    ColorTemplate.rgb("#A3FF12"),
    ColorTemplate.rgb("#E0E805"),
    ColorTemplate.rgb("#FFE805")
)

class GraphicsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphics)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        getRequest(::activityLoad, queue)
    }

    private fun activityLoad(response: JSONObject) {
        val entries: MutableList<PieEntry> = ArrayList()

        for (key: String in response.keys()) {
            entries.add(PieEntry(response.getDouble(key).toFloat(), key))
        }

        entries.sortByDescending { pieEntry -> pieEntry.value }

        val set = PieDataSet(entries, "Trip types")
        val data = PieData(set)
        val colors: MutableList<Int> = ArrayList()
        for (col: Int in COLORS) {
            colors.add(col)
        }
        set.colors = colors
        pieChart.data = data
        // Description
        val desc = Description()
        desc.text = "estadisticas de viajes"
        desc.textSize = 15F
        desc.textAlign = Paint.Align.CENTER
        desc.setPosition(pieChart.center.x, pieChart.center.y)

        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15F)
        pieChart.setEntryLabelTextSize(10F)
        pieChart.description = desc
        pieChart.setHoleColor(getColor(R.color.transparent))
        pieChart.invalidate() // refresh

        val legend = pieChart.legend
        legend.isEnabled = false
    }

    private fun messageError(error: VolleyError) {
        Log.e("Network issue", error.message.toString())
        Toast.makeText(
            applicationContext,
            "Error no encontramos una red disponible",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getRequest(function: (r: JSONObject) -> Unit, queue: RequestQueue) {
        // Request a object response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://raw.githubusercontent.com/Arep-Nico/template/master/date_test",
            null,
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