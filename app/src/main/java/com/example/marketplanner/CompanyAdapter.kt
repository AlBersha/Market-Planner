package com.example.marketplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompanyAdapter(private val companiesList: List<Company>)
    : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.companyLogoView)
        val companyTitleTextView: TextView = view.findViewById(R.id.companyTitleTextView)
        val companySubtitleTextView: TextView = view.findViewById(R.id.companySubtitleTextView)
        val currentCostTextView: TextView = view.findViewById(R.id.currentCostTextView)
        val costDifferenceTextView: TextView = view.findViewById(R.id.costDifferenceTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_of_companies, parent, false)

        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        var currentItem = companiesList[position]

        holder.imageView.setImageResource(currentItem.imageLogo)
        holder.companyTitleTextView.text = currentItem.companyName
        holder.companySubtitleTextView.text = currentItem.companySubtitle
        holder.currentCostTextView.text = currentItem.currentCost.toString()
        holder.costDifferenceTextView.text = currentItem.costDifference.toString()
    }

    override fun getItemCount() = companiesList.size
}