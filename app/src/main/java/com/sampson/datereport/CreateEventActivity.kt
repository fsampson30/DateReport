package com.sampson.datereport

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.sampson.datereport.controller.returnDateReversed
import com.sampson.datereport.controller.returnNotifyDate
import com.sampson.datereport.controller.returnTodaysDateString

import com.sampson.datereport.model.Event

class CreateEventActivity : AppCompatActivity() {
    val event = Event()
    val newEvent = mutableListOf<Event>()

    lateinit var txtEventTitle: EditText
    lateinit var txtInitialDate: EditText
    lateinit var txtDaysToNotify: EditText
    lateinit var txtNotifyDate: EditText
    lateinit var cbIsNotifiable: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        txtEventTitle = findViewById(R.id.edtCreateEventTitle)
        txtInitialDate = findViewById(R.id.edtCreateEventInitialDate)
        txtDaysToNotify = findViewById(R.id.edtCreateEventDaysToRemember)
        txtNotifyDate = findViewById(R.id.edtCreateEventNotifyDate)
        cbIsNotifiable = findViewById(R.id.cbCreateEventIsNofifiable)
        val toolbar = findViewById<Toolbar>(R.id.createToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtInitialDate.append(returnTodaysDateString())
        txtInitialDate.isEnabled = false

        txtNotifyDate.isEnabled = false


        txtDaysToNotify.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                txtNotifyDate.text.clear()
                txtNotifyDate.append(returnNotifyDate(txtDaysToNotify.text.toString().toInt()))
                true
            } else {
                false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.event_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.eventMenuConfirm -> {
                if (txtEventTitle.text.isEmpty() || txtDaysToNotify.text.isEmpty()) {
                    txtEventTitle.error = "Empty Field"
                    txtDaysToNotify.error = "Empty Field"
                } else {
                    if (txtNotifyDate.text.isEmpty()) {
                        txtNotifyDate.append(returnNotifyDate(txtDaysToNotify.text.toString().toInt()))
                    } else {
                        getScreenInformation()
                        val replyIntent = Intent()
                        replyIntent.putExtra("event", newEvent[0])
                        setResult(Activity.RESULT_OK, replyIntent)
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getScreenInformation() {
        event.title = txtEventTitle.text.toString()
        event.inicialDate = returnDateReversed(txtInitialDate.text.toString())
        event.daysToNotify = txtDaysToNotify.text.toString().toInt()
        event.isNotifiable = cbIsNotifiable.isChecked
        event.finishDate = ""
        event.isOpen = true
        event.notifyDate = returnDateReversed(txtNotifyDate.text.toString())
        newEvent.add(event)
    }
}
