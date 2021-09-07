package com.sampson.datereport.controller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sampson.datereport.R
import com.sampson.datereport.model.Event

class EventAdapter(
    val context : Context

) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var events = mutableListOf<Event>()


    class EventViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txtEventName = itemView.findViewById<TextView>(R.id.txtItemEventName)
        val btnNotifiable = itemView.findViewById<ImageButton>(R.id.imgBtnItemEventNotify)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.txtEventName.text = events[position].title
        Log.d("EventAdapter",events[position].title)
        holder.btnNotifiable.setOnClickListener {
            Toast.makeText(context,"Notify",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = events.size

    fun submitList(events : MutableList<Event>){
        this.events = events
        notifyDataSetChanged()
    }


}