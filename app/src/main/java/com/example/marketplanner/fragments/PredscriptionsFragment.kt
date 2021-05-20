package com.example.marketplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.marketplanner.models.CompanyModel
import com.example.marketplanner.adapters.CompanyAdapter
import com.example.marketplanner.R
import com.example.marketplanner.adapters.ViewPagerAdapter
import com.example.marketplanner.databinding.FragmentPredscriptionsBinding
import com.google.android.material.tabs.TabLayout

class PredscriptionsFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    companion object {
        fun newInstance(): PredscriptionsFragment {
            val fragment = PredscriptionsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentPredscriptionsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_predscriptions, container, false)

        viewPager = binding.viewPredscriptionPager
        tabLayout = binding.predscriptionTabLayout
        tabLayout.setupWithViewPager(viewPager)

        setupViewPager(viewPager)

        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(PredictionsFragment(), "Predictions")
        adapter.addFragment(RecommendationsFragment(), "Recommendations")
        viewPager.adapter = adapter

    }
}