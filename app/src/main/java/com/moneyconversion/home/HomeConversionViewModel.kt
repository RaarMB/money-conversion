package com.moneyconversion.home

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.moneyconversion.model.Money
import com.moneyconversion.network.MoneyConversionRepository
import com.moneyconversion.network.MoneyConversionRepository.Companion.MXN_CODE
import com.moneyconversion.network.MoneyConversionRepository.Companion.USD_CODE
import com.moneyconversion.R
import com.moneyconversion.model.HomeConversionAction
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeConversionViewModel @Inject constructor(
    private val repository: MoneyConversionRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val moneyList = listOf(
        Money(MXN_CODE, ContextCompat.getDrawable(context, R.drawable.ic_mxn_flag), true),
        Money(USD_CODE, ContextCompat.getDrawable(context, R.drawable.ic_usd_flag), false)
    )

    private var moniesFrom = listOf(
        Money(MXN_CODE, ContextCompat.getDrawable(context, R.drawable.ic_mxn_flag), true),
        Money(USD_CODE, ContextCompat.getDrawable(context, R.drawable.ic_usd_flag), false)
    )

    private var moniesTo = listOf(
        Money(MXN_CODE, ContextCompat.getDrawable(context, R.drawable.ic_mxn_flag), false),
        Money(USD_CODE, ContextCompat.getDrawable(context, R.drawable.ic_usd_flag), true)
    )

    fun getMoniesFrom() = MutableLiveData<List<Money>>().apply { value = moniesFrom }

    fun getMoniesTo() = MutableLiveData<List<Money>>().apply { value = moniesTo }

    private val _action = MutableLiveData<HomeConversionAction>()
    val action: LiveData<HomeConversionAction> = _action

    val amount = MutableLiveData<String>()
    val conversionResult = MutableLiveData<String>()
    val showProgress = MutableLiveData<Boolean>()

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
        selectedMoneyFrom.value = money
        selectedMoneyTo.value =
            moneyList.first { it.id != money.id }.copy(selected = money.selected)
    }

    fun selectedMoneyTo(money: Money) {
        selectedMoneyTo.value = money
        selectedMoneyFrom.value =
            moneyList.first { it.id != money.id }.copy(selected = money.selected)
    }

    fun conversion() {
        hideKeyBoard()
        conversionResult.value = ""
        viewModelScope.launch {
            showProgress.value = true
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
            } finally {
                showProgress.value = false
            }
        }
    }

    val isConversionEnabled = MediatorLiveData<Boolean>().apply {
        addSource(amount) { value = validateInformation() }
        addSource(selectedMoneyItemFrom) { value = validateInformation() }
        addSource(selectedMoneyItemTo) { value = validateInformation() }
    }

    private fun validateInformation() =
        amount.value != null && selectedMoneyItemFrom.value != null &&
                selectedMoneyItemTo.value != null

    fun hideKeyBoard() {
        _action.value = HomeConversionAction.HideKeyboard
    }
}