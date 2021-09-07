package com.sampson.datereport

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sampson.datereport.controller.EventAdapter
import com.sampson.datereport.controller.EventsApplication
import com.sampson.datereport.controller.returnDayofWeek
import com.sampson.datereport.controller.returnTodaysDate
import com.sampson.datereport.model.Event
import com.sampson.datereport.model.EventViewModel
import com.sampson.datereport.model.EventViewModelFactory



class MainActivity : AppCompatActivity() {

    private val newEventActivityRequestCode = 1
    private val eventViewModel : EventViewModel by viewModels {
        EventViewModelFactory((application as EventsApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventViewModel.delete()


        val txtDate = findViewById<TextView>(R.id.txtMainAcivityTodayDate)
        val fabAddEvent = findViewById<FloatingActionButton>(R.id.fltBtnAddEvent)
        val rvEventList = findViewById<RecyclerView>(R.id.rvMainActivityEvents)

        val adapter = EventAdapter(baseContext)
        rvEventList.layoutManager = LinearLayoutManager(baseContext)
        rvEventList.adapter = adapter

        txtDate.text = " ${returnDayofWeek()} ${returnTodaysDate()}"

        eventViewModel.allEvents.observe(this) { events ->
            events.let { adapter.submitList(it) }
        }

        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val event = it.data?.getSerializableExtra("event") as Event
                eventViewModel.insert(event)
            } else {
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }

        fabAddEvent.setOnClickListener {
            val intent = Intent(baseContext,CreateEventActivity::class.java)
            register.launch(intent)
        }
    }

}