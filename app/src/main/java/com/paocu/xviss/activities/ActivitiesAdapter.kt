package com.paocu.xviss.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paocu.xviss.R


class ActivitiesAdapter(private val dataSet: ArrayList<Activity>) :
    RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Activity) {
            // Get elements reference in the activity view
            val title: TextView = itemView.findViewById(R.id.titleCardView)
            val location: TextView = itemView.findViewById(R.id.locationView)
            val description: TextView = itemView.findViewById(R.id.descriptionCardView)
            val imageCard: ImageView = itemView.findViewById(R.id.imageCardView)
            // Set the values
            title.text = data.title
            location.text = data.location
            description.text = data.description
            // Set image source
            Glide.with(itemView.context).load(data.image).into(imageCard)
            // define click action on the image
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "You click on Activity ${data.title}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItems(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}