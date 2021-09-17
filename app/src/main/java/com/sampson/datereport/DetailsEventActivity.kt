package com.sampson.datereport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.sampson.datereport.controller.returnDateCorrect
import com.sampson.datereport.model.Event

class DetailsEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_event)

        val txtNameEvent = findViewById<EditText>(R.id.edtDetailsEventTitle)
        val txtDaysEvent = findViewById<EditText>(R.id.edtDetailsEventDaysToRemember)
        val txtInitialDate = findViewById<EditText>(R.id.edtDetailsEventInitialDate)
        val txtNotifyDate = findViewById<EditText>(R.id.edtDetailsEventNotifyDate)
        val cbNotify = findViewById<CheckBox>(R.id.cbDetailsEventIsNofifiable)
        val toolbar = findViewById<Toolbar>(R.id.DetailsToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val event = intent.getSerializableExtra("event") as Event

        txtNameEvent.append(event.title)
        txtDaysEvent.append(event.daysToNotify.toString())
        txtInitialDate.append(returnDateCorrect(event.inicialDate))
        txtNotifyDate.append(returnDateCorrect(event.notifyDate))
        cbNotify.isChecked = event.isNotifiable
        txtNameEvent.isEnabled = false
        txtDaysEvent.isEnabled = false
        txtInitialDate.isEnabled = false
        txtNotifyDate.isEnabled = false
        cbNotify.isEnabled = false

    }
}