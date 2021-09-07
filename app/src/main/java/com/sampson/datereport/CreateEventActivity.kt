package com.sampson.datereport

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import com.sampson.datereport.model.Event

class CreateEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val txtEventTitle = findViewById<EditText>(R.id.edtCreateEventTitle)
        val txtInitialDate = findViewById<EditText>(R.id.edtCreateEventDaysToRemember)
        val txtDaysToNotify = findViewById<EditText>(R.id.edtCreateEventDaysToRemember)
        val cbIsNotifiable = findViewById<CheckBox>(R.id.cbCreateEventIsNofifiable)
        val imgBtnAccept = findViewById<ImageButton>(R.id.imgBtnCreateEventAccept)

        imgBtnAccept.setOnClickListener {
            val event = Event()
            event.title = txtEventTitle.text.toString()
            event.inicialDate = txtInitialDate.text.toString()
            event.daysToNotify = if(txtDaysToNotify.text.isEmpty()) 0 else txtDaysToNotify.text.toString().toInt()
            event.isNotifiable = cbIsNotifiable.isChecked
            event.finishDate = ""
            event.isOpen = true
            event.notifyDate = ""

            val newEvent = mutableListOf<Event>()
            newEvent.add(event)

            Log.d("Create",event.title)
            val replyIntent = Intent()
            replyIntent.putExtra("event", newEvent[0])
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}