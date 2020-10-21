package com.example.navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation.R

class SecondScreenFragment : Fragment() {
    val args: SecondScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second_screen, container, false)
        var count = args.count
        val countDisplay = view.findViewById<TextView>(R.id.display_count)
        countDisplay.text = count.toString()
        view.findViewById<Button>(R.id.increase_count).setOnClickListener {
            val action = SecondScreenFragmentDirections.actionSecondScreenFragmentSelf(++count)
            it.findNavController().navigate(action)
            println(it.findNavController())
        }
        return view
    }
}