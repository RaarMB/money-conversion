package com.moneyconversion.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.moneyconversion.NavigationToolbarDelegate
import com.moneyconversion.databinding.FragmentSplashConversionBinding

class SplashConversionFragment : Fragment() {

    private var _binding: FragmentSplashConversionBinding? = null

    private val binding get() = _binding!!

    private var listener: SplashConversionListener? = null

    private var navigationToolbarDelegate: NavigationToolbarDelegate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashConversionBinding.inflate(inflater, container, false)
        navigationToolbarDelegate?.hideToolbar()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? SplashConversionListener
        navigationToolbarDelegate = context as? NavigationToolbarDelegate
    }

    override fun onStart() {
        super.onStart()
        binding.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                listener?.goToHome()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        navigationToolbarDelegate = null
    }

    interface SplashConversionListener {
        fun goToHome()
    }
}