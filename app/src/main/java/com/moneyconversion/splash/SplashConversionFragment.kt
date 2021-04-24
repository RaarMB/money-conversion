package com.moneyconversion.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moneyconversion.databinding.FragmentSplashConversionBinding

class SplashConversionFragment : Fragment() {

    private var _binding: FragmentSplashConversionBinding? = null

    private val binding get() = _binding!!

    private var listener: SplashConversionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashConversionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? SplashConversionListener
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            context?.let {
                listener?.goToHome()
            }
        }, TIME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface SplashConversionListener {
        fun goToHome()
    }

    private companion object {
        const val TIME = 1200L
    }
}