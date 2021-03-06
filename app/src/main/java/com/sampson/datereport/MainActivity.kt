package com.sampson.datereport

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

    private val newEventActivityRequestCode = 1
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
        val rdoAllEvents = findViewById<RadioButton>(R.id.rdoBtnMainActivityAll)
        val rdoBtnGreen = findViewById<RadioButton>(R.id.rdoBtnMainActivityGreen)
        val rdoBtnYellow = findViewById<RadioButton>(R.id.rdoBtnMainActivityYellow)
        val rdoBtnRed = findViewById<RadioButton>(R.id.rdoBtnMainActivityRed)

        setSupportActionBar(toolbar)

        val adapter = EventAdapter(baseContext)
        rvEventList.layoutManager = LinearLayoutManager(baseContext)
        rvEventList.adapter = adapter

        txtDate.text = " ${returnDayofWeek()} ${returnTodaysDateString()}"

        rdoAllEvents.isChecked = true
        eventViewModel.allEvents.observe(this) { events ->
            events.let { adapter.submitList(it) }
        }

        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val event = it.data?.getSerializableExtra("event") as Event
                eventViewModel.insert(event)
                rdoAllEvents.isChecked = true
                eventViewModel.allEvents.observe(this) { events ->
                    events.let { adapter.submitList(it) }
                }
            }
        }

        rdoAllEvents.setOnClickListener {
            rdoAllEvents.isChecked = true
            eventViewModel.allEvents.observe(this) { events ->
                events.let { adapter.submitList(it) }
            }
        }

        rdoBtnGreen.setOnClickListener {
            rdoBtnGreen.isChecked = true
            eventViewModel.onTimeEvents.observe(this) { events ->
                events.let { adapter.submitList(it) }
            }
        }

        rdoBtnYellow.setOnClickListener {
            rdoBtnYellow.isChecked = true
            eventViewModel.warningEvents.observe(this) { events ->
                events.let { adapter.submitList(it) }
            }
        }

        rdoBtnRed.setOnClickListener {
            rdoBtnRed.isChecked = true
            eventViewModel.expiredEvents.observe(this) { events ->
                events.let { adapter.submitList(it) }
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
                rdoAllEvents.isChecked = true
                eventViewModel.allEvents.observe(this@MainActivity) { events ->
                    events.let { adapter.submitList(it) }
                }
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