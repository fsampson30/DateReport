package com.sampson.datereport

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sampson.datereport.controller.EventAdapter
import com.sampson.datereport.controller.EventsApplication
import com.sampson.datereport.controller.returnDayofWeek
import com.sampson.datereport.controller.returnTodaysDateString
import com.sampson.datereport.model.Event
import com.sampson.datereport.model.EventViewModel
import com.sampson.datereport.model.EventViewModelFactory
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    //private val newEventActivityRequestCode = 1
    private val eventViewModel: EventViewModel by viewModels {
        EventViewModelFactory((application as EventsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtDate = findViewById<TextView>(R.id.txtMainAcivityTodayDate)
        val fabAddEvent = findViewById<FloatingActionButton>(R.id.fltBtnAddEvent)
        val rvEventList = findViewById<RecyclerView>(R.id.rvMainActivityEvents)
        val toolbar = findViewById<Toolbar>(R.id.mainToolbar)

        setSupportActionBar(toolbar)


        val adapter = EventAdapter(baseContext)
        rvEventList.layoutManager = LinearLayoutManager(baseContext)
        rvEventList.adapter = adapter

        txtDate.text = " ${returnDayofWeek()} ${returnTodaysDateString()}"

        eventViewModel.allEvents.observe(this) { events ->
            events.let { adapter.submitList(it) }
        }

        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val event = it.data?.getSerializableExtra("event") as Event
                eventViewModel.insert(event)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        fabAddEvent.setOnClickListener {
            val intent = Intent(baseContext, CreateEventActivity::class.java)
            register.launch(intent)
        }

        val helper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val event = adapter.getEventAtPosition(position)
                eventViewModel.deleteEvent(event)
            }
        }
        val itemTouchHelper = ItemTouchHelper(helper)
        itemTouchHelper.attachToRecyclerView(rvEventList)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemDeleteAll -> {
                showAlertDialog("This action will clear the list..." +
                        " Confirm?", null, View.OnClickListener {
                    eventViewModel.delete()
                })
            }
            R.id.itemExit -> {
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(
        title: String,
        view: View?,
        positiveClickListener: View.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                positiveClickListener.onClick(null)
            }.show()
    }

}