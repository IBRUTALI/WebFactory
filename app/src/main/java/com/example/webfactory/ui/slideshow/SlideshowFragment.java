package com.example.webfactory.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.R;
import com.example.webfactory.adapter.CategoryAdapter;
import com.example.webfactory.model.Category;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {


    private SlideshowViewModel slideshowViewModel;
    List<Category> categoryList1= new ArrayList<>();
    private int count=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        Button br = root.findViewById(R.id.button_review);

        //Подруб адаптера и списка
        RecyclerView recyclerView = root.findViewById(R.id.categoryRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList1);
        recyclerView.setAdapter(categoryAdapter);

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewOnWindow();
            }
        });


        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void showReviewOnWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());

        dialog.setPositiveButton("Оставить отзыв", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editText1 = alertDialog.findViewById(R.id.reviewTitle);
                EditText editText2 = alertDialog.findViewById(R.id.reviewDescription);
                String a1 = editText1.getText().toString();
                String a2 = editText2.getText().toString();

                categoryList1.add(new Category(count, a1, a2));
                count++;
                showToast();
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
        dialog.show();
    }

    public void showToast() {
        Toast.makeText(getContext(), R.string.toast_review, Toast.LENGTH_SHORT).show();
    }


}