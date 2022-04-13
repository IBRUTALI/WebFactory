package com.example.webfactory.ui.polls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.webfactory.adapter.PollsAdapter;
import com.example.webfactory.databinding.FragmentPollsBinding;
import com.example.webfactory.model.Category;
import com.example.webfactory.ui.polls.PollsViewModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PollsFragment extends Fragment {
    private FragmentPollsBinding binding;
    private DBHelperReview dBHelperReview1;
    private DBHelperReview dBHelperReview2;
    private ArrayList<String> polls_id, polls_title, polls_var1, polls_var2, polls_var3; // TODO: 01.03.2022  добавить возможность выбора количества вопросов при создании анкеты
    private ArrayList<String> categoryList;

    public PollsFragment() {
        super(R.layout.fragment_polls);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dBHelperReview1 = new DBHelperReview(getContext());
        dBHelperReview2 = new DBHelperReview(getContext());
        polls_id = new ArrayList<>();
        polls_title = new ArrayList<>();
        polls_var1 = new ArrayList<>();
        polls_var2 = new ArrayList<>();
        polls_var3 = new ArrayList<>();

        categoryList = new ArrayList<>();

        storeDataInArrays();

        //Подруб адаптера и списка
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.pollsRecycler.setLayoutManager(layoutManager);
        PollsAdapter pollsAdapter = new PollsAdapter(getContext(), polls_id, polls_title, polls_var1, polls_var2, polls_var3);
        binding.pollsRecycler.setAdapter(pollsAdapter);

        binding.buttonPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPollsOnWindow();
            }
        });

        return root;
    }

    void storeDataInArrays() {
        Cursor cursor1 = dBHelperReview1.readAllData();
        Cursor cursor2 = dBHelperReview2.readAllDataP();
        if (cursor2.getCount() == 0) {
            Toast.makeText(getContext(), "Нет данных ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor2.moveToNext()) {
                polls_id.add(cursor2.getString(0));
                polls_title.add(cursor2.getString(1));
                polls_var1.add(cursor2.getString(2));
                polls_var2.add(cursor2.getString(3));
                polls_var3.add(cursor2.getString(4));
            }
            if (cursor1.getCount() == 0) {
                Toast.makeText(getContext(), "Нет данных ", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor1.moveToNext()) {
                    categoryList.add(cursor1.getString(3));
                }
            }
        }
        Map<String, Long> frequency =
                categoryList.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));


        frequency.forEach((k, v) -> {
            String check = "";
            for (int i = 0; i < polls_title.size(); i++) {
                if (polls_title.get(i).equals("Автоматическая анкета: " + k)) {
                    check = "Автоматическая анкета: " + k;
                    return;
                }
            }
            if (v >= 2 && !check.equals("Автоматическая анкета: " + k)) {
                DBHelperReview myDB = new DBHelperReview(getContext());
                String a1 = "Автоматическая анкета: " + k;
                String a2 = "";
                String a3 = "";
                String a4 = "";
                switch (k){
                    case "Столовая":
                         a2 = "Еда вкусная?";
                         a3 = "Еда качественная?";
                         a4 = "Посуда чистая?";
                        break;
                    case "Производство":
                        a2 = "Станки безопасные?";
                        a3 = "Освещение хорошее?";
                        a4 = "Воздух чистый?";
                        break;
                    case "Быт":
                        a2 = "Туалеты хорошие?";
                        a3 = "Уборщицы убираются?";
                        a4 = "Мыло есть?";
                        break;
                    case "Руководство":
                        a2 = "Руководство хорошее?";
                        a3 = "Руководство часто приходит?";
                        a4 = "Руководство заслуживает свою зп?";
                        break;
                    case "Другое":
                        a2 = "Что вам нравится?";
                        a3 = "Хотите чебурек?";
                        a4 = "Как вам моя машина?";
                        break;
                }
                myDB.addPolls(a1, a2, a3, a4);

            }
        });
    }

    private void showPollsOnWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());

        dialog.setPositiveButton("Создать анкету", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DBHelperReview myDB = new DBHelperReview(getContext());

                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editText1 = alertDialog.findViewById(R.id.pollsAlertTitle);
                EditText editText2 = alertDialog.findViewById(R.id.pollsAlertVar1);
                EditText editText3 = alertDialog.findViewById(R.id.pollsAlertVar2);
                EditText editText4 = alertDialog.findViewById(R.id.pollsAlertVar3);

                String a1 = editText1.getText().toString();
                String a2 = editText2.getText().toString();
                String a3 = editText3.getText().toString();
                String a4 = editText4.getText().toString();

                myDB.addPolls(a1, a2, a3, a4);
                alertDialog.dismiss();

            }

        });

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ConstraintLayout cl1 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_polls, null);
        dialog.setView(cl1);
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
