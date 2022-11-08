package com.example.cupcake.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00


class OrderViewModel : ViewModel() {
    val dateOptions = getPickupOptions()


    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price

    init{
        resetOrder()
    }

    fun setQuantity(numCupcakes: Int) {
        _quantity.value = numCupcakes
        updatePrice()
    }

    fun setFlavor(newFlavour: String) {
        _flavor.value = newFlavour
    }

    fun setDate(d: String) {
        _date.value = d
        updatePrice()
    }
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun updatePrice(){
        var value = PRICE_PER_CUPCAKE * quantity.value!!
        if(_date.value == dateOptions[0]){
            value += PRICE_FOR_SAME_DAY_PICKUP
            println("True")
        }
        println(value)
        _price.value = value
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d",Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE,1)
        }
        return options
    }

}