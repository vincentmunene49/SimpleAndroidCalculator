package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var text:StringBuffer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button1.setOnClickListener{
            val buttonText = (it as Button).text.toString()
            text?.append(buttonText)
            binding.computeView.text = buttonText


        }
        binding.button2.setOnClickListener{
            val buttonText = (it as Button).text.toString()
            text?.append(buttonText)
            binding.computeView.text = buttonText


        }

        //function that takes in a button
        //get the text of the button
        //while user is typing, we append this string and  set the text view to the string
    }
    private fun showUserInput(button: Button){

            var text:StringBuffer?=null
            val button_text = button.text.toString()
            text?.append(button_text)
            binding.computeView.text = text

    }
}