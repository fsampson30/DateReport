package com.sampson.datereport

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar

import com.sampson.datereport.model.Event
import kotlin.system.exitProcess

class CreateEventActivity : AppCompatActivity() {
    val event = Event()
    val newEvent = mutableListOf<Event>()

    lateinit var txtEventTitle : EditText
    lateinit var txtInitialDate : EditText
    lateinit var txtDaysToNotify : EditText
    lateinit var cbIsNotifiable : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        txtEventTitle = findViewById(R.id.edtCreateEventTitle)
        txtInitialDate = findViewById(R.id.edtCreateEventInitialDate)
        txtDaysToNotify = findViewById(R.id.edtCreateEventDaysToRemember)
        cbIsNotifiable = findViewById(R.id.cbCreateEventIsNofifiable)
        val toolbar = findViewById<Toolbar>(R.id.createToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.event_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.eventMenuConfirm -> {
                getScreenInformation()
                val replyIntent = Intent()
                replyIntent.putExtra("event", newEvent[0])
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
                }
            }
        return super.onOptionsItemSelected(item)
    }

    private fun getScreenInformation(){
        event.title = txtEventTitle.text.toString()
        event.inicialDate = txtInitialDate.text.toString()
        event.daysToNotify = if(txtDaysToNotify.text.isEmpty()) 0 else txtDaysToNotify.text.toString().toInt()
        event.isNotifiable = cbIsNotifiable.isChecked
        event.finishDate = ""
        event.isOpen = true
        event.notifyDate = ""
        newEvent.add(event)
    }
}