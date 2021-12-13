package com.example.webfactory.ui.polls;

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
import com.example.webfactory.adapter.PollsAdapter;
import com.example.webfactory.ui.polls.PollsViewModel;

public class PollsFragment extends Fragment {

    private PollsViewModel pollsViewModel;
    private int count=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pollsViewModel =
                new ViewModelProvider(this).get(PollsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_polls, container, false);
        final TextView textView = root.findViewById(R.id.text_polls);

        //Подруб адаптера и списка
        RecyclerView recyclerView = root.findViewById(R.id.pollsRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        PollsAdapter pollsAdapter = new PollsAdapter(getActivity(), pollsViewModel.pollsList);
        recyclerView.setAdapter(pollsAdapter);

        Button bp = root.findViewById(R.id.button_polls);
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPollsOnWindow();
            }
        });
        pollsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    private void showPollsOnWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());

        dialog.setPositiveButton("Создать анкету", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editText1 = alertDialog.findViewById(R.id.pollsAlertTitle);
                EditText editText2 = alertDialog.findViewById(R.id.pollsAlertVar1);
                EditText editText3 = alertDialog.findViewById(R.id.pollsAlertVar2);
                EditText editText4 = alertDialog.findViewById(R.id.pollsAlertVar3);

                String a1 = editText1.getText().toString();
                String a2 = editText2.getText().toString();
                String a3 = editText3.getText().toString();
                String a4 = editText4.getText().toString();

                pollsViewModel.addItem(count, a1, a2, a3, a4);
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
        ConstraintLayout cl1 = (ConstraintLayout) getLayoutInflater().inflate(R.layout.alert_polls, null);
        dialog.setView(cl1);
        dialog.show();
    }

    public void showToast() {
        Toast.makeText(getContext(), R.string.toast_polls, Toast.LENGTH_SHORT).show();
    }

}