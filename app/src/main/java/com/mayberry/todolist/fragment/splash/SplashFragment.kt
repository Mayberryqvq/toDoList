package com.mayberry.todolist.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mayberry.todolist.R
import com.mayberry.todolist.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        binding.skipBtn.setOnClickListener {
            timer.cancel()
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        timer = Timer()
        timer.schedule(object: TimerTask() {
            override fun run() {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }
            }

        }, 3000)
    }

}