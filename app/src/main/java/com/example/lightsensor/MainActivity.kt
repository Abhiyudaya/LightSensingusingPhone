package com.example.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener { //Add SensorEventListener and add the following implementations.

    private var sensor : Sensor ?= null
    private var sensorManager : SensorManager ?= null
    private lateinit var image : ImageView
    private lateinit var background : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        image = findViewById(R.id.displayImage)
        background = findViewById(R.id.background)

        image.visibility=View.INVISIBLE
    }
    //From the Google Developers documentations.
    override fun onResume() {
        //Register a listener for the sensor.
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }
    //From the Google Developers documentations.
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event != null) {
                Log.d(TAG,"onSensorChanged : "+event.values[0])
            }
            if (event != null) {
                if(event.values[0] < 30){
                    //Light is dim.
                    image.visibility= View.INVISIBLE
                    background.setBackgroundColor(resources.getColor(R.color.black))
                } else{
                    //Show torch if light intensity is good.
                    image.visibility = View.VISIBLE
                }
            }
        } catch (e:IOException){
            Log.d(TAG,"onSensorChanged : ${e.message}")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}