package com.example.marketplanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplanner.R
import com.example.marketplanner.fragments.CompanyDetailsFragment
import com.example.marketplanner.models.CompanyModel

class CompanyAdapter(
    private val companiesList: List<CompanyModel>,
    private val listener: OnItemClickListener )
    : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener {
        val imageView: ImageView = view.findViewById(R.id.companyLogoView)
        val companyTitleTextView: TextView = view.findViewById(R.id.companyTitleTextView)
        val companySubtitleTextView: TextView = view.findViewById(R.id.companySubtitleTextView)
        val currentCostTextView: TextView = view.findViewById(R.id.currentCostTextView)
        val costDifferenceTextView: TextView = view.findViewById(R.id.costDifferenceTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_of_companies, parent, false)

        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        var currentItem = companiesList[position]

        holder.imageView.setImageResource(currentItem.imageLogo)
        holder.companyTitleTextView.text = currentItem.name
        holder.companySubtitleTextView.text = currentItem.description
        holder.currentCostTextView.text = currentItem.currentCost.toString()
        holder.costDifferenceTextView.text = currentItem.costDifference.toString()

    }

    override fun getItemCount() = companiesList.size

}