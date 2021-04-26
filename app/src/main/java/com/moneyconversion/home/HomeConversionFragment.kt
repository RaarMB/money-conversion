package com.moneyconversion.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moneyconversion.NavigationToolbarDelegate
import com.moneyconversion.databinding.FragmentHomeConversionBinding
import com.moneyconversion.model.Money
import com.moneyconversion.utils.configureNotFilterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeConversionFragment : Fragment() {

    private lateinit var binding: FragmentHomeConversionBinding

    private val viewModel: HomeConversionViewModel by viewModels()

    private var navigationToolbarDelegate: NavigationToolbarDelegate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeConversionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navigationToolbarDelegate?.showToolbar()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationToolbarDelegate = context as? NavigationToolbarDelegate
    }

    override fun onDetach() {
        super.onDetach()
        navigationToolbarDelegate = null
    }

    private fun bindViewModel() {
        viewModel.getMoniesFrom().observe(viewLifecycleOwner, Observer(this::configureMoneyFromField))
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