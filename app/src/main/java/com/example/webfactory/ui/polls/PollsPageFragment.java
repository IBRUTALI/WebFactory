package com.example.webfactory.ui.polls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentPollsPageBinding;

import java.util.ArrayList;

public class PollsPageFragment extends Fragment {
    private FragmentPollsPageBinding binding;
    private ArrayList<String> varList;
    private String id, title, var1, var2, var3;
    private NavController navController;


    public PollsPageFragment() {
        super(R.layout.fragment_polls_page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getAndSetIntentData();

        binding.sendPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPolls();
            }
        });
        return root;
    }


    void getAndSetIntentData() {
        if (getArguments() != null) {

            id = getArguments().getString("pollsId");
            title = getArguments().getString("pollsTitle");
            var1 = getArguments().getString("pollsVar1");
            var2 = getArguments().getString("pollsVar2");
            var3 = getArguments().getString("pollsVar3");

            binding.pollsPageTitle.setText(title);
            varList = new ArrayList<>();
            varList.add(var1);
            varList.add(var2);
            varList.add(var3);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.polls_list_item, R.id.pollsPageTVItem, varList);
            binding.pollsPageLV.setAdapter(arrayAdapter);
        } else {
            Toast.makeText(getContext(), "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPolls() {
        // TODO: 01.03.2022 Возможность отправки результатов анкетирования с сохранением в БД
        Toast.makeText(getContext(), "Анкета отправлена!", Toast.LENGTH_SHORT).show();
        navController.popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
