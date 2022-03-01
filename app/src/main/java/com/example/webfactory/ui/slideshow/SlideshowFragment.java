 package com.example.webfactory.ui.slideshow;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.webfactory.Databases.DBHelperReview;
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
    DBHelperReview dBHelperReview;
    ArrayList<String> review_id, review_title, review_description;

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

        dBHelperReview =new DBHelperReview(getContext());
        review_id = new ArrayList<>();
        review_title = new ArrayList<>();
        review_description = new ArrayList<>();

        storeDataInArrays();


        //Подруб адаптера и списка
        RecyclerView recyclerView = root.findViewById(R.id.categoryRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), getContext(), review_id, review_title, review_description);
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

            DBHelperReview myDB = new DBHelperReview(getContext());


                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editText1 = alertDialog.findViewById(R.id.reviewTitle);
                EditText editText2 = alertDialog.findViewById(R.id.reviewDescription);
                String a1 = editText1.getText().toString();
                String a2 = editText2.getText().toString();
                myDB.addReview(a1, a2);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

        }
    }

    void storeDataInArrays(){
        Cursor cursor = dBHelperReview.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "Нет данных ", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                review_id.add(cursor.getString(0));
                review_title.add(cursor.getString(1));
                review_description.add(cursor.getString(2));
            }
        }
    }


}