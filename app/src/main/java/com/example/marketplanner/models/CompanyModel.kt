package com.example.marketplanner.models

data class CompanyModel (val imageLogo: Int, val companyName: String, var companySubtitle: String,
                         val currentCost: Float, val costDifference: Float)