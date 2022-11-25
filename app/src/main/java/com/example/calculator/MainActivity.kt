package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.Character.isDigit

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var canAddOperator = false
    private var canAddDecimal = true
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.button0.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.equals.setOnClickListener(this)
        binding.minus.setOnClickListener(this)
        binding.multiply.setOnClickListener(this)
        binding.divide.setOnClickListener(this)
        binding.add.setOnClickListener(this)
        binding.clear.setOnClickListener(this)
        binding.backspace.setOnClickListener(this)
        binding.decimal.setOnClickListener(this)
        binding.brackets.setOnClickListener(this)
    }


    override fun onClick(button: View?) {

        when (button?.id) {
            R.id.clear -> allClearOperation()
            R.id.backspace -> onBackspaceOperation()
            R.id.button_0 -> onNumbersOperation(button)
            R.id.button_1 -> onNumbersOperation(button)
            R.id.button_2 -> onNumbersOperation(button)
            R.id.button_3 -> onNumbersOperation(button)
            R.id.button_4 -> onNumbersOperation(button)
            R.id.button_5 -> onNumbersOperation(button)
            R.id.button_6 -> onNumbersOperation(button)
            R.id.button_7 -> onNumbersOperation(button)
            R.id.button_8 -> onNumbersOperation(button)
            R.id.button_9 -> onNumbersOperation(button)
            R.id.minus -> operatorOperation(button)
            R.id.decimal -> onNumbersOperation(button)
            R.id.divide -> operatorOperation(button)
            R.id.multiply -> operatorOperation(button)
            R.id.equals -> onEqualsOperation()
            R.id.add -> operatorOperation(button)

        }
    }

    private fun onNumbersOperation(view: View) {
        val button: Button = view as Button
        if (button.text.toString() == ".") {

            if (canAddDecimal) {
                binding.computeView.append(button.text)
                canAddDecimal = false
            }
        } else {
            binding.apply {
                computeView.append(button.text)
            }
            canAddOperator = true
        }
    }

    private fun onEqualsOperation() {
        binding.answerView.text = calculateResults()
    }

    private fun calculateResults(): String {
        val obtainedList = obtainListOfOperationsAndNumbers()
        if (obtainedList.isEmpty()) return ""
        //division and multiply
        val divisionAndMultiplication = divisionAndMultiplication(obtainedList)
        if (divisionAndMultiplication.isEmpty()) return ""

        //add and subtract
        val finalResult = addAndSubtract(divisionAndMultiplication)

        return finalResult.toString()
    }

    private fun addAndSubtract(passedList: MutableList<Any>): Float {
        var result = passedList[0].toString().toFloat()

        for (index in passedList.indices) {
            if (passedList[index] is Char && index != passedList.lastIndex) {
                val operator = passedList[index]
                val next = passedList[index + 1].toString().toFloat()

                if (operator == '+')
                    result += next
                if (operator == '-')
                    result -= next
            }
        }


        return result

    }

    private fun divisionAndMultiplication(passed_list: MutableList<Any>): MutableList<Any> {
        var list = passed_list
        while (list.contains('*') || list.contains('/')) {
            list = divideAndMultiplied(list)
        }


        return list
    }

    private fun divideAndMultiplied(list: MutableList<Any>): MutableList<Any> {
        val dividedAndMultipliedList = mutableListOf<Any>()
        var restartIndex = list.size

        for (index in list.indices) {
            if (list[index] is Char && index != list.lastIndex && index < restartIndex) {
                val operator = list[index]
                val previousNumber = list[index - 1].toString().toFloat()
                val nextNumber = list[index + 1].toString().toFloat()


                when (operator) {
                    '*' -> {
                        dividedAndMultipliedList.add(previousNumber * nextNumber)
                        restartIndex = index + 1
                    }
                    '/' -> {
                        dividedAndMultipliedList.add(previousNumber / nextNumber)
                        restartIndex = index + 1
                    }
                    else -> {
                        dividedAndMultipliedList.add(previousNumber)
                        dividedAndMultipliedList.add(operator)
                    }
                }
            }

            if (index > restartIndex) {
                dividedAndMultipliedList.add(list[index])
            }
        }


        return dividedAndMultipliedList
    }

    private fun obtainListOfOperationsAndNumbers(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""

        for (character in binding.computeView.text) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else {
                list.add(currentDigit)
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "") {
            list.add(currentDigit)
        }




        return list
    }

    private fun onBackspaceOperation() {
        binding.apply {
            val length: Int = computeView.length()
            if (length > 0) {
                val newString: String = computeView.text.substring(0, length - 1)
                computeView.setText(newString)
            }


        }
    }

    private fun allClearOperation() {
        binding.computeView.text = ""
    }

    private fun operatorOperation(view: View) {
        val length: Int = binding.computeView.length()
        val lastChar: Char = binding.computeView.text.toString()[length - 1]
        if (isDigit(lastChar)) {

            canAddOperator = true
        }
        if (canAddOperator) {
            binding.computeView.append((view as Button).text)
            canAddOperator = false
            canAddDecimal = true
        }

    }
}