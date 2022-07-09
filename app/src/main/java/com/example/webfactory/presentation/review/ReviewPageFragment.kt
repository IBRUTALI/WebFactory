package com.example.webfactory.presentation.review

import com.example.webfactory.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.data.databases.DBHelperReview
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.webfactory.databinding.FragmentReviewPageBinding

class ReviewPageFragment : Fragment(R.layout.fragment_review_page) {
    private var binding: FragmentReviewPageBinding? = null
    private var id: String? = null
    private var title: String? = null
    private var description: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewPageBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        andSetIntentData
//        binding!!.updateReview.setOnClickListener {
//            val dBHelperReview =
//                DBHelperReview(context)
//            title = binding!!.reviewPageTitle.text.toString().trim { it <= ' ' }
//            description = binding!!.reviewPageDescription.text.toString().trim { it <= ' ' }
//            dBHelperReview.updateData(id, title, description)
  //      }
        binding!!.deleteReview.setOnClickListener { deleteReview() }
        return root
    }

    private val andSetIntentData: Unit
        get() {
            if (arguments != null) {
                id = arguments!!.getString("reviewId")
                title = arguments!!.getString("reviewTitle")
                description = arguments!!.getString("reviewDescription")
                binding!!.reviewPageTitle.setText(title)
                binding!!.reviewPageDescription.setText(description)
            } else {
                Toast.makeText(context, "Нет данных", Toast.LENGTH_SHORT).show()
            }
        }

    private fun deleteReview() {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}