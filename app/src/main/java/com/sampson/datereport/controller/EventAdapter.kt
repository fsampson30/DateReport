package com.sampson.datereport.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sampson.datereport.CreateEventActivity
import com.sampson.datereport.DetailsEventActivity
import com.sampson.datereport.R
import com.sampson.datereport.model.Event

class EventAdapter(
    val context: Context

) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var events = mutableListOf<Event>()


    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtEventName = itemView.findViewById<TextView>(R.id.txtItemEventName)
        val btnNotifiable = itemView.findViewById<ImageButton>(R.id.imgBtnItemEventNotify)
        val llEvent = itemView.findViewById<LinearLayout>(R.id.llEventItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.txtEventName.text = events[position].title
        val correct = returnDateCorrect(events[position].notifyDate)
        Log.i("ERROR", correct.toString())
        val days = returnDaysToExpire(correct)
        when (days.toInt()) {
            in -100..0 -> {
                holder.llEvent.background = ContextCompat.getDrawable(context,R.drawable.shape_red)
                holder.btnNotifiable.background = ContextCompat.getDrawable(context,R.drawable.shape_red)
            }
            in 1..2 -> {
                holder.llEvent.background = ContextCompat.getDrawable(context,R.drawable.shape_yellow)
                holder.btnNotifiable.background = ContextCompat.getDrawable(context,R.drawable.shape_yellow)
            }
            in 3..100 -> {
                holder.llEvent.background = ContextCompat.getDrawable(context,R.drawable.shape_green)
                holder.btnNotifiable.background = ContextCompat.getDrawable(context,R.drawable.shape_green)
            }
        }
        holder.btnNotifiable.setOnClickListener {
            val intent = Intent(context, DetailsEventActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("event", events[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = events.size

    fun submitList(events: MutableList<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    fun getEventAtPosition(position: Int): Event {
        return events[position]
    }

}