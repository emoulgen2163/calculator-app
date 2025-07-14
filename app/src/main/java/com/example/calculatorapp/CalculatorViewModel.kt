package com.example.calculatorapp


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import kotlin.math.pow

class CalculatorViewModel: ViewModel() {

    private val _operation = MutableLiveData("")
    val operation: LiveData<String> = _operation

    private val _result = MutableLiveData("0")
    val result: LiveData<String> = _result

    private var currentOperator: String? = null
    private var firstOperand: String = ""
    private var isSecondOperand = false

    fun onButtonClick(button: String){

        operation.value?.let {

            when(button){
                "AC" -> {
                    _operation.value = ""
                    _result.value = "0"
                    firstOperand = ""
                    currentOperator = null
                    isSecondOperand = false
                }

                "<-" -> {
                    if (it.isNotEmpty()){
                        _operation.value = it.substring(0, it.length - 1)
                        return
                    }
                }

                "=" -> {
                    if (currentOperator != null && firstOperand.isNotEmpty()){
                        val secondOperand = _operation.value!!.substringAfter("$firstOperand$currentOperator")
                        val result = calculate(firstOperand, secondOperand, currentOperator!!)
                        _operation.value = result
                        _result.value = result

                        currentOperator = null
                        firstOperand = ""
                        isSecondOperand = false
                    }
                }

                "+/-" -> {
                     if (_operation.value!!.startsWith("-")){
                         _operation.value = _operation.value!!.replace("-", "")
                     } else{
                         _operation.value = "-${_operation.value}"
                     }
                }

                "+", "-", "*", "/", "^" -> {
                    if (!isSecondOperand && _operation.value!!.isNotEmpty()){
                        firstOperand = _operation.value!!
                        currentOperator = button
                        _operation.value = "$firstOperand$button"
                        isSecondOperand = true
                    }

                }

                else -> _operation.value = it + button

            }
        }

    }

    fun calculate(o1: String, o2: String, operand: String): String{
        val num1 = o1.toDoubleOrNull()!!
        val num2 = o2.toDoubleOrNull()!!

        var result = when(operand){
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
            "^" -> num1.pow(num2).toString()
            else -> "Error"
        }

        if (result.endsWith(".0")){
            result = result.replace(".0", "")
        }

        return result

    }
}