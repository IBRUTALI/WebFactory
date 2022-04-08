package com.example.webfactory.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.Databases.DBHelperReview;
import com.example.webfactory.R;
import com.example.webfactory.adapter.CategoryAdapter;
import com.example.webfactory.databinding.FragmentReviewBinding;
import com.example.webfactory.model.Category;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewFragment extends Fragment {
    private FragmentReviewBinding binding;
    private CategoryAdapter categoryAdapter;
    private DBHelperReview dBHelperReview;
    private ArrayList<Category> categoryList;

    public ReviewFragment() {
        super(R.layout.fragment_review);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dBHelperReview = new DBHelperReview(getContext());
        categoryList = new ArrayList<>();

        storeDataInArrays();

        //Подруб адаптера и списка
        RecyclerView recyclerView = root.findViewById(R.id.categoryRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        recyclerView.setAdapter(categoryAdapter);

        binding.tabLayoutReview.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                categoryAdapter.getFilter().filter(tab.getText());
             }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewOnWindow();
            }
        });

        return root;
    }

    private void showReviewOnWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        String[] categoryList = {"Столовая", "Производство", "Быт", "Руководство", "Другое"};
        String[] selectedCategory = new String[1];
        dialog.setTitle("Создание отзыва");
        dialog.setPositiveButton("Оставить отзыв", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DBHelperReview myDB = new DBHelperReview(getContext());

                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editText1 = alertDialog.findViewById(R.id.reviewTitle);
                EditText editText2 = alertDialog.findViewById(R.id.reviewDescription);
                String a1 = editText1.getText().toString();
                String a2 = editText2.getText().toString();
                String a3 = selectedCategory[0];
                myDB.addReview(a1, a2, a3);
                alertDialog.dismiss();
            }
        });

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_review, null);
        dialog.setView(cl);

        dialog.setSingleChoiceItems(categoryList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedCategory[0] = categoryList[which];
            }
        });
        dialog.show();
    }


    private void storeDataInArrays() {
        Cursor cursor = dBHelperReview.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Нет данных ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                categoryList.add(new Category(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}