package com.example.webfactory.adapter

import android.content.Context
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.webfactory.adapter.ReviewAdapter.ReviewViewHolder
import androidx.navigation.NavController
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.webfactory.R
import android.os.Bundle
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.domain.models.Review
import java.util.*
import kotlin.collections.ArrayList

class ReviewAdapter(
    private val context: Context,
    private var reviewList: ArrayList<Review>,
    ) : RecyclerView.Adapter<ReviewViewHolder>(), Filterable {

    var reviewListFull = ArrayList<Review>()

    init {
        reviewListFull = reviewList
    }


    private var navController: NavController? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val reviewItems = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(reviewItems)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.reviewTitle.text = reviewList[position].title.toString()
        holder.reviewId.text = reviewList[position].id.toString()
        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("reviewId", reviewList[holder.adapterPosition].id.toString())
            bundle.putString("reviewTitle", reviewList[holder.adapterPosition].title.toString())
            bundle.putString("reviewDescription", reviewList[holder.adapterPosition].description.toString())
            navController = findNavController(view)
            navController!!.navigate(R.id.action_nav_forum_to_reviewPageFragment, bundle)
        }
    }

    override fun getFilter(): Filter {
        return reviewFilter
    }

    private val reviewFilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filteredList: ArrayList<Review> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList = reviewListFull
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                for (item in reviewListFull) {
                    if (item.category.lowercase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            reviewList = results.values as ArrayList<Review>
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reviewTitle: TextView = itemView.findViewById(R.id.reviewItemTitle)
        var reviewId: TextView = itemView.findViewById(R.id.reviewItemId)
        //var reviewFragment: ConstraintLayout = itemView.findViewById(R.id.reviewFragment)

    }

}