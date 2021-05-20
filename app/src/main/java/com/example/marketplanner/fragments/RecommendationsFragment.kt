package com.example.marketplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplanner.models.CompanyModel
import com.example.marketplanner.adapters.CompanyAdapter
import com.example.marketplanner.R
import com.example.marketplanner.databinding.FragmentRecommendationsBinding

class RecommendationsFragment : Fragment() , CompanyAdapter.OnItemClickListener {
    private val companiesList = generateDummyList()
    private val adapter = CompanyAdapter(companiesList, this)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRecommendationsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_recommendations, container, false)

        val companiesList = generateDummyList()
        binding.recyclerRecommendationsView.adapter = adapter
        binding.recyclerRecommendationsView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerRecommendationsView.setHasFixedSize(true)

        return binding.root
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(this, "The item was clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = companiesList[position]
        clickedItem.companySubtitle = "Clicked"
        adapter.notifyItemChanged(position)
    }

    private fun generateDummyList(): List<CompanyModel> {
        val list = ArrayList<CompanyModel>()
        list.add(0, CompanyModel(R.drawable.ic_android, "Google", "Technology company", currentCost = 2262.47F, costDifference = -26.45F))
        list.add(1, CompanyModel(R.drawable.ic_android, "Tesla", "Electric car company", currentCost = 577.87F, costDifference = 1.04F))
        list.add(2, CompanyModel(R.drawable.ic_android, "LVMH", "Luxury goods company", currentCost = 623.00F, costDifference = -1.00F))
        list.add(3, CompanyModel(R.drawable.ic_android, "Hewlett-Packard", "Computer hardware company", currentCost = 32.34F, costDifference = -0.70F))
        list.add(4, CompanyModel(R.drawable.ic_android, "Samsung Electronics", "Electronics company", currentCost = 79600F, costDifference = 0F))
        return list
    }
}