package com.example.marketplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplanner.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val companiesList = generateDummyList()
        binding.recyclerHomeView.adapter = CompanyAdapter(companiesList)
        binding.recyclerHomeView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerHomeView.setHasFixedSize(true)

        return binding.root
    }

    private fun generateDummyList(): List<Company> {
        val list = ArrayList<Company>()
        list.add(0, Company(R.drawable.ic_android, "Google", "Technology company", currentCost = 2262.47F, costDifference = -26.45F))
        list.add(1, Company(R.drawable.ic_android, "Tesla", "Electric car company", currentCost = 577.87F, costDifference = 1.04F))
        list.add(2, Company(R.drawable.ic_android, "LVMH", "Luxury goods company", currentCost = 623.00F, costDifference = -1.00F))
        list.add(3, Company(R.drawable.ic_android, "Hewlett-Packard", "Computer hardware company", currentCost = 32.34F, costDifference = -0.70F))
        list.add(4, Company(R.drawable.ic_android, "Samsung Electronics", "Electronics company", currentCost = 79600F, costDifference = 0F))
        return list
    }
}