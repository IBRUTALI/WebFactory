package com.example.webfactory.presentation.review

import android.app.AlertDialog
import com.example.webfactory.R
import com.example.webfactory.adapter.ReviewAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.models.Review
import com.example.webfactory.databinding.FragmentReviewBinding
import java.util.ArrayList

class ReviewFragment : Fragment(R.layout.fragment_review) {
    private var binding: FragmentReviewBinding? = null
    private var reviewAdapter: ReviewAdapter? = null
    private var reviewList: ArrayList<Review>? = null
    private lateinit var viewModel: ReviewFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentReviewBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        viewModel = ViewModelProvider(this, ReviewFragmentViewModelFactory(requireContext()))
            .get(ReviewFragmentViewModel::class.java)

        reviewList = viewModel.readReview()

        //Adapter
        val recyclerView: RecyclerView = root.findViewById(R.id.categoryRecycler)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        reviewAdapter = context?.let { ReviewAdapter(it, reviewList!!) }
        recyclerView.adapter = reviewAdapter

        binding!!.tabLayoutReview.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                reviewAdapter?.filter?.filter(tab.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}

        })

        binding!!.buttonReview.setOnClickListener { showReviewOnWindow() }
        return root
    }

    private fun showReviewOnWindow() {
        val dialog = AlertDialog.Builder(requireContext())
        val categoryList = arrayOf("Столовая", "Производство", "Быт", "Руководство", "Другое")
        val selectedCategory = arrayOfNulls<String>(1)
        dialog.setTitle("Создание отзыва")
        dialog.setPositiveButton("Оставить отзыв") { dialogInterface, i ->
            val alertDialog = dialogInterface as AlertDialog
            val titleEditText = alertDialog.findViewById<EditText>(R.id.reviewTitle)
            val descriptionEditText = alertDialog.findViewById<EditText>(R.id.reviewDescription)
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val category = selectedCategory[0]
            val review = Review("0", title, description, category!!)
            viewModel.addReview(review = review)
            alertDialog.dismiss()
        }
        dialog.setNegativeButton("Отмена") { dialogInterface, i -> }
        val cl = layoutInflater.inflate(R.layout.alert_review, null) as ConstraintLayout
        dialog.setView(cl)
        dialog.setSingleChoiceItems(categoryList, -1) { dialog, which ->
            selectedCategory[0] = categoryList[which]
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}