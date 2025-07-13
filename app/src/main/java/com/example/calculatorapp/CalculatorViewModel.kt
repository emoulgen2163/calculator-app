package com.example.calculatorapp


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {

    private val _operation = MutableLiveData("")
    val operation: LiveData<String> = _operation

    private val _result = MutableLiveData("0")
    val result: LiveData<String> = _result


    fun onButtonClick(button: String){

        operation.value?.let {

            when(button){
                "AC" -> {
                    _operation.value = ""
                    _result.value = "0"
                    return
                }

                "C" -> {
                    if (it.isNotEmpty()){
                        _operation.value = it.substring(0, it.length - 1)
                        return
                    }
                }

                "=" -> {
                    _operation.value = _result.value
                    return
                }
            }

            _operation.value = it + button

            try {
                _result.value = calculation(_operation.value!!)
            } catch (e: Exception) {
            }
        }

    }

    fun calculation(operation: String): String{
        val context = Context.enter()
        context.optimizationLevel = -1
        val scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, operation, "Javascript", 1, null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }
}