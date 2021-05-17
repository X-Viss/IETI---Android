package com.paocu.xviss.trips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paocu.xviss.R

class TripAdapter(private val dataSet: ArrayList<TripType>) :
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: TripType) {
            // Get elements reference in the activity view
            val title: TextView = itemView.findViewById(R.id.titleCardView)
            val location: TextView = itemView.findViewById(R.id.locationView)
            val description: TextView = itemView.findViewById(R.id.descriptionCardView)
            val imageCard: ImageView = itemView.findViewById(R.id.imageCardView)
            // Set the values
            title.text = data.title
            description.text = data.description
            // Set image source
            Glide.with(itemView.context).load(data.image).into(imageCard)
            // define click action on the image
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "You click on Trip ${data.title}",
                    Toast.LENGTH_LONG
                ).show()
                data.clickFunction(data.title, data.image, data.description)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}