package com.example.marketplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.marketplanner.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
//            inflater, R.id.menuFragment, container, false)

        return inflater.inflate(R.layout.fragment_menu, container, false)
//        return binding.root
    }
}