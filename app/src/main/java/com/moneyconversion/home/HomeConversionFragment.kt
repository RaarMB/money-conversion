package com.moneyconversion.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moneyconversion.databinding.FragmentHomeConversionBinding
import com.moneyconversion.model.Money
import com.moneyconversion.utils.configureNotFilterAdapter

class HomeConversionFragment : Fragment() {

    private lateinit var binding: FragmentHomeConversionBinding

    private val viewModel: HomeConversionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeConversionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getMoniesFrom()
            .observe(viewLifecycleOwner, Observer(this::configureMoneyFromField))
        viewModel.getMoniesTo().observe(viewLifecycleOwner, Observer(this::configureMoneyToField))
    }

    private fun configureMoneyFromField(monies: List<Money>) {
        binding.autocompleteTextViewFrom.configureNotFilterAdapter(monies) {
            viewModel.selectedMoneyFrom(it.copy(selected = true))
        }
    }

    private fun configureMoneyToField(monies: List<Money>) {
        binding.autocompleteTextViewTo.configureNotFilterAdapter(monies) {
            viewModel.selectedMoneyTo(it.copy(selected = true))
        }
    }

}