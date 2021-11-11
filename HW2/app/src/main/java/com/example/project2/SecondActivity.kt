package com.example.project2

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.SeekBar.OnSeekBarChangeListener




class SecondActivity : AppCompatActivity() {
    private lateinit var colorBlock: TextView
    private lateinit var redBar: SeekBar
    private lateinit var blueBar: SeekBar
    private lateinit var greenBar: SeekBar
    private lateinit var redText: TextView
    private lateinit var greenText: TextView
    private lateinit var blueText: TextView
    private lateinit var colorView: TextView

    fun twoDigit(s: String): String {
        if(s.length<2) return "0$s".uppercase()
        return s.uppercase()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val intent = Intent(this, MainActivity::class.java)
        if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        colorBlock=findViewById(R.id.ColorBlock)
        redBar=findViewById(R.id.RedBar)
        blueBar=findViewById(R.id.BlueBar)
        greenBar=findViewById(R.id.GreenBar)
        redText=findViewById(R.id.RedText)
        blueText=findViewById(R.id.BlueText)
        greenText=findViewById(R.id.GreenText)
        colorView=findViewById(R.id.ColorView)

        redBar.max=255
        blueBar.max=255
        greenBar.max=255

        var red=0
        var green=0
        var blue=0
        redBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                red=p1
                colorBlock.setBackgroundColor(Color.rgb(red,green,blue))
                colorView.text="#"+twoDigit(red.toString(16))+twoDigit(green.toString(16))+twoDigit(blue.toString(16))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        greenBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                green=p1
                colorBlock.setBackgroundColor(Color.rgb(red,green,blue))
                colorView.text="#"+twoDigit(red.toString(16))+twoDigit(green.toString(16))+twoDigit(blue.toString(16))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        blueBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                blue=p1
                colorBlock.setBackgroundColor(Color.rgb(red,green,blue))
                colorView.text="#"+twoDigit(red.toString(16))+twoDigit(green.toString(16))+twoDigit(blue.toString(16))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }
}