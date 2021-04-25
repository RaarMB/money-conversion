package com.moneyconversion.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.moneyconversion.model.ConversionResult
import com.moneyconversion.model.Money
import com.moneyconversion.network.MoneyConversionRepository
import com.moneyconversion.network.MoneyConversionRepository.Companion.MXN_CODE
import com.moneyconversion.network.MoneyConversionRepository.Companion.USD_CODE
import com.moneyconversion.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeConversionViewModel @Inject constructor(
    private val repository: MoneyConversionRepository
) : ViewModel() {

    private var moniesFrom = listOf(
        Money(MXN_CODE, R.mipmap.ic_mxn_flag, true),
        Money(USD_CODE, R.mipmap.ic_usd_flag, false)
    )

    private var moniesTo = listOf(
        Money(MXN_CODE, R.mipmap.ic_mxn_flag, false),
        Money(USD_CODE, R.mipmap.ic_usd_flag, true)
    )

    fun getMoniesFrom() = MutableLiveData<List<Money>>().apply { value = moniesFrom }

    fun getMoniesTo()= MutableLiveData<List<Money>>().apply { value = moniesTo }

    val amount = MutableLiveData<String>()
    val conversionResult = MutableLiveData<String>()

    private val selectedMoneyFrom = MediatorLiveData<Money>().apply {
        addSource(getMoniesFrom()) { monies ->
            value = monies.first { it.selected }
        }
    }

    private val selectedMoneyTo = MediatorLiveData<Money>().apply {
        addSource(getMoniesTo()) { monies ->
            value = monies.first { it.selected }
        }
    }

    val selectedMoneyItemFrom = MediatorLiveData<Money>().apply {
        addSource(selectedMoneyFrom) { value = it }
    }

    val selectedMoneyItemTo = MediatorLiveData<Money>().apply {
        addSource(selectedMoneyTo) { value = it }
    }

    fun selectedMoneyFrom(money: Money) {
        moniesFrom.forEach {
            money.copy(selected = it.id == money.id)
        }
        moniesTo.forEach {
            money.copy(selected = it.id != money.id)
        }
        selectedMoneyFrom.value = money
    }

    fun selectedMoneyTo(money: Money) {
        moniesFrom.forEach {
            money.copy(selected = it.id != money.id)
        }
        moniesTo.forEach {
            money.copy(selected = it.id == money.id)
        }
        selectedMoneyTo.value = money
    }

    fun conversion() {
        Transformations.map(
            repository.getConversion(
                selectedMoneyFrom.value?.id.toString(),
                selectedMoneyTo.value?.id.toString(),
                amount.value.toString()
            )
        )
        {
            conversionResult.value = when (it) {
                is ConversionResult.ConversionSuccess -> it.result
                is ConversionResult.ConversionError -> it.errorMessage
            }
        }
    }
}