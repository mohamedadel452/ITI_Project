package com.example.iti_project.ui.actitvity1.mainActitvity.signupSuccessful

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.iti_project.R

class CongratsFragment : Fragment() {

    private lateinit var btn_continue: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congrats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_continue = view.findViewById(R.id.btn_continue)
        btn_continue.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.loginFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
        }

    }
}