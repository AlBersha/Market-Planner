package com.example.marketplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CompanyDetailsFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding: FragmentCompanyDetailsBinding = DataBindingUtil.inflate(
//            inflater, R.id.companyDetailsFragment, container, false)
//        return binding.root
        return inflater.inflate(R.layout.fragment_company_details, container, false)
    }

}