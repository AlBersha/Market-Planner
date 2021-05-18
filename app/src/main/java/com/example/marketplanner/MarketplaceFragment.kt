package com.example.marketplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.marketplanner.databinding.FragmentMarketplaceBinding

class MarketplaceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentMarketplaceBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_marketplace, container, false)

//        binding.marketplaceToCompanyButton.setOnClickListener {
//            view: View -> view.findNavController().navigate(R.id.action_marketplaceFragment_to_companyDetailsFragment2)
//        }
//
//        binding.marketplaceToReportsButton.setOnClickListener {
//                view: View -> view.findNavController().navigate(R.id.action_marketplaceFragment_to_reportsFragment2)
//        }
//
//        binding.marketplaceToSettingsButton.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_marketplaceFragment_to_settingsFragment)
//        }

        return binding.root
    }
}