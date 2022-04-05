package com.example.webfactory.ui.polls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentPollsPageBinding;

public class PollsPageFragment extends Fragment {
    private FragmentPollsPageBinding binding;
    private String id, title, var1, var2, var3;


    public PollsPageFragment() {
        super(R.layout.fragment_polls_page);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getAndSetIntentData();
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
            binding.pollsPageVar1.setText(var1);
            binding.pollsPageVar2.setText(var2);
            binding.pollsPageVar3.setText(var3);
        } else {
            Toast.makeText(getContext(), "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPolls() {
        // TODO: 01.03.2022 Возможность отправки результатов анкетирования с сохранением в БД 
    }

    private void deletePolls() {
        // TODO: 01.03.2022 Возможность удаление анкеты 
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
