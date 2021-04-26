package com.moneyconversion.home

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.moneyconversion.model.Money
import com.moneyconversion.network.MoneyConversionRepository
import com.moneyconversion.network.MoneyConversionRepository.Companion.MXN_CODE
import com.moneyconversion.network.MoneyConversionRepository.Companion.USD_CODE
import com.moneyconversion.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeConversionViewModel @Inject constructor(
    private val repository: MoneyConversionRepository,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    private var moniesFrom = listOf(
        Money(MXN_CODE, ContextCompat.getDrawable(context, R.drawable.ic_mxn_flag), true),
        Money(USD_CODE, ContextCompat.getDrawable(context, R.drawable.ic_usd_flag), false)
    )

    private var moniesTo = listOf(
        Money(MXN_CODE, ContextCompat.getDrawable(context, R.drawable.ic_mxn_flag), false),
        Money(USD_CODE, ContextCompat.getDrawable(context, R.drawable.ic_usd_flag), true)
    )

    fun getMoniesFrom() = MutableLiveData<List<Money>>().apply { value = moniesFrom }

    fun getMoniesTo()= MutableLiveData<List<Money>>().apply { value = moniesTo }

    val amount = MutableLiveData<String>()
    var conversionResult = MutableLiveData<String>()

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
        val moniesFromUpdate = moniesFrom.map {
            it.copy(selected = it.id == money.id)
        }
        val moniesToUpdate = moniesTo.map {
            it.copy(selected = it.id != money.id)
        }
        selectedMoneyFrom.value = moniesFromUpdate.first { it.selected }
        selectedMoneyTo.value = moniesToUpdate.first { it.selected }
    }

    fun selectedMoneyTo(money: Money) {
        val moniesFromUpdate = moniesFrom.map {
            it.copy(selected = it.id != money.id)
        }
        val moniesToUpdate = moniesTo.map {
            it.copy(selected = it.id == money.id)
        }
        selectedMoneyTo.value = moniesToUpdate.first { it.selected }
        selectedMoneyFrom.value = moniesFromUpdate.first { it.selected }
    }

    fun conversion() {
        viewModelScope.launch {
            try {
                val response = repository.getConversion(
                    selectedMoneyFrom.value?.id.toString(),
                    selectedMoneyTo.value?.id.toString(),
                    amount.value.toString()
                )
                conversionResult.value = when (response.success) {
                    true -> String.format("%.2f", response.result)
                    else -> response.error?.info.toString()
                }
            } catch (exception: Exception) {
                conversionResult.value = exception.message.toString()
            }
        }
    }

    val isConversionEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(amount) { value = validateInformation() }
        addSource(selectedMoneyItemFrom) { value = validateInformation() }
        addSource(selectedMoneyItemTo) { value = validateInformation() }
        addSource(conversionResult) { value = validateInformation() }
    }

    private fun validateInformation() =
        amount.value != null && selectedMoneyItemFrom.value != null &&
                selectedMoneyItemTo.value != null && conversionResult.value != null
}