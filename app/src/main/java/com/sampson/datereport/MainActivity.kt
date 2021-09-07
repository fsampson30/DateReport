package com.sampson.datereport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sampson.datereport.controller.returnDayofWeek
import com.sampson.datereport.controller.returnTodaysDate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtDate = findViewById<TextView>(R.id.txtMainAcivityTodayDate)
        val fabAddEvent = findViewById<FloatingActionButton>(R.id.fltBtnAddEvent)
        val rvEventList = findViewById<RecyclerView>(R.id.rvMainActivityEvents)

        txtDate.text = " ${returnDayofWeek()} ${returnTodaysDate()}"

        fabAddEvent.setOnClickListener {
            Toast.makeText(this,"Adding",Toast.LENGTH_SHORT).show()
        }
    }

}